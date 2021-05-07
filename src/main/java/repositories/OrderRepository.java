/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import database.DatabaseAdapter;
import entities.Customer;
import entities.Order;
import entities.OrderProduct;
import entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */
public class OrderRepository implements IOrderRepository {

    private DatabaseAdapter _adapter;
    
    public OrderRepository(DatabaseAdapter adapter) {
        _adapter = adapter;
    }

    @Override
    public double getTotalPrice(int id) {
        String sql = "select sum(p.price * op.amount) as total_price from orders o\n" +
            "join orders_products op on op.order_id=o.id\n" +
            "join products p on p.id=op.product_id\n" +
            "where o.id=?"; 
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getDouble("total_price");
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta do preço total do pedido", ex);
        }
    }

    @Override
    public Order getById(int id) {
        String sql = "select o.id, c.id as customer_id, c.name as customer_name, c.phone as customer_phone, sum(p.price * op.amount) as total_price, o.open_date, o.close_date from orders o\n" +
            "join customers c on c.id=o.customer_id\n" +
            "join orders_products op on op.order_id=o.id\n" +
            "join products p on p.id=op.product_id\n" +
            "where o.id=?\n" + 
            "group by o.id;";
        String sqlOrderProduct = "select op.id, op.product_id, p.name as product_name, op.amount from orders_products op " +
            "join products p on p.id=op.product_id "+
            "where op.order_id=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            Order order = mapOrder(result);
            
            preparedStatement = connection.prepareStatement(sqlOrderProduct,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            
            result = preparedStatement.executeQuery();
            while(result.next()) {
                OrderProduct orderProduct = mapOrderProductList(result);
                order.addOrderProduct(orderProduct);
            }
            
            return order;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta do pedido pelo id", ex);
        }
    }

    @Override
    public ArrayList<Order> getAll() {
        String sql = "select o.id, o.customer_id, c.name as customer_name, c.phone as customer_phone, sum(p.price * op.amount) as total_price, o.open_date, o.close_date, o.status from orders o\n" +
            "join customers c on c.id=o.customer_id\n" +
            "join orders_products op on op.order_id=o.id\n" +
            "join products p on p.id=op.product_id\n" +
            "group by o.id"; 
        Connection connection = _adapter.getConnection();
        try {
            Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            ResultSet result = statement.executeQuery(sql);
            ArrayList<Order> orders = new ArrayList<Order>();
            while(result.next())
                orders.add(mapOrderList(result));
            
            return orders;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta dos pedidos", ex);
        }
    }

    @Override
    public void create(Order order) {
        String sqlInsert = "insert into orders (user_id, customer_id, open_date) values(?, ?, ?);";
        String sqlLastId = "select max(id) as id from orders;";
        Connection connection = _adapter.getConnection();
        try {
            _adapter.beginTransaction();
            
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            mapCreateOrderParams(preparedStatement, order);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum pedido foi inserido");
            
            preparedStatement = connection.prepareStatement(sqlLastId,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            order.setId(result.getInt("id"));
            
            rows = insertOrderProducts(connection, order.getOrderProducts(), order.getId());
            if (rows == 0) throw new JPedidosException("Nenhum produto do pedido foi inserido");
            
            _adapter.commit();
        } catch(SQLException ex) {
            _adapter.rollback();
            throw new JPedidosException("Falha na execução do comando para inserir novo usuário", ex);
        }
    }

    @Override
    public void update(Order order) {
        String sql = "update orders set customer_id=?, status=?, close_date=? where id=?;";
        Connection connection = _adapter.getConnection();
        try {
            _adapter.beginTransaction();
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            mapUpdateOrderParams(preparedStatement, order);
            preparedStatement.setInt(4, order.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum pedido foi atualizado");
            
            String sqlOrderProductSelect = "select id from orders_products where order_id=?;";
            preparedStatement = connection.prepareStatement(sqlOrderProductSelect,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, order.getId());
            
            ArrayList<Integer> orderProductIds = new ArrayList<>();
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                int orderProductId = result.getInt("id");
                orderProductIds.add(orderProductId);
            }
            
            ArrayList<Integer> orderProductIdsRemove = getOrderProductIdsToRemove(order.getOrderProducts(), orderProductIds);
            if (orderProductIdsRemove.size() > 0)
                deleteOrderProductIds(connection, orderProductIdsRemove);
            
            ArrayList<OrderProduct> orderProductUpdate = getOrderProductToUpdate(order.getOrderProducts(), orderProductIds);
            if (orderProductUpdate.size() > 0) 
                updateOrderProducts(connection, orderProductUpdate, order.getId());
            
            ArrayList<OrderProduct> orderProductInsert = getOrderProductToInsert(order.getOrderProducts(), orderProductIds);
            if (orderProductInsert.size() > 0)
                insertOrderProducts(connection, orderProductInsert, order.getId());
            
            _adapter.commit();
        } catch(SQLException ex) {
            _adapter.rollback();
            throw new JPedidosException("Falha na execução do comando para atualizar pedido", ex);
        }
    }

    @Override
    public void delete(Order order) {
        String sqlDelete = "delete from orders where id=?;";
        String sqlOrderProductsDelete = "delete from orders_products where order_id=?;";
        Connection connection = _adapter.getConnection();
        try {
            _adapter.beginTransaction();
            
            PreparedStatement preparedStatement = connection.prepareStatement(sqlOrderProductsDelete,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            preparedStatement.setInt(1, order.getId());
            preparedStatement.executeUpdate();
            
            preparedStatement = connection.prepareStatement(sqlDelete,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            preparedStatement.setInt(1, order.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum pedido foi removido");
            
            _adapter.commit();
        } catch(SQLException ex) {
            _adapter.rollback();
            throw new JPedidosException("Falha na execução do comando para remover pedidos", ex);
        }
    }
    
    private int insertOrderProducts(
        Connection connection, 
        List<OrderProduct> orderProducts,
        int orderId
    ) throws SQLException {
        String sqlOrderProductInsert = "insert into orders_products (id, order_id, product_id, amount) values(?, ?, ?, ?); ";
        String sqlOrderProductLastId = "select max(id) as id from orders_products; ";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlOrderProductLastId,
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        ResultSet result = preparedStatement.executeQuery();
        result.next();
        int lastIdOrderProduct =result.getInt("id");

        
        int rows = 0;
        for (OrderProduct op : orderProducts) {
            preparedStatement = connection.prepareStatement(sqlOrderProductInsert,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            op.setId(orderProducts.indexOf(op) + lastIdOrderProduct + 1);
            op.setOrderId(orderId);
            mapCreateOrderProdutctParams(preparedStatement, op);
            
            rows += preparedStatement.executeUpdate();
        }

        return rows;
    }
    
    private int updateOrderProducts(
        Connection connection, 
        List<OrderProduct> orderProducts,
        int orderId
    ) throws SQLException {
        String sqlOrderProductUpdate = "update orders_products set product_id=?, amount=? where id=?; ";

        int rows = 0;
        for (OrderProduct op : orderProducts) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlOrderProductUpdate,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            op.setOrderId(orderId);
            mapUpdateOrderProdutctParams(preparedStatement, op);
            rows += preparedStatement.executeUpdate();
        }

        return rows;
    }
    
    private int deleteOrderProductIds(
        Connection connection, 
        List<Integer> orderProductIds
    ) throws SQLException {
        String sqlOrderProductDelete = "delete from orders_products where id in (" + getSqlParamIds(orderProductIds.size()) + ");";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlOrderProductDelete,
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE
        );
        int offset = 0;
        for (Integer id : orderProductIds) {
            preparedStatement.setInt(1 + offset, id);
            offset++;
        }

        return preparedStatement.executeUpdate();
    }
    
    private String getSqlParamIds(int size) {
        List<String> params = new ArrayList<>();
        
        for (int i = 0; i < size; i ++)
            params.add("?");
        
        return String.join(", ", params);
    }
    
    private ArrayList<OrderProduct> getOrderProductToInsert(List<OrderProduct> source, ArrayList<Integer> persistedIds) {
        ArrayList<OrderProduct> orderProductsToInsert = new ArrayList<>();
        for (OrderProduct op : source) {
            boolean founded = false;
            for (Integer id : persistedIds){
                if (op.getId() == id) founded = true;
            }
            if (!founded) orderProductsToInsert.add(op);
        }
        
        return orderProductsToInsert;
    }
    
    private ArrayList<OrderProduct> getOrderProductToUpdate(List<OrderProduct> source, ArrayList<Integer> persistedIds) {
        ArrayList<OrderProduct> orderProductsToUpdate = new ArrayList<>();
        for (OrderProduct op : source) {
            for (Integer id : persistedIds){
                if (op.getId() == id) {
                    orderProductsToUpdate.add(op);
                }
            }
        }
        
        return orderProductsToUpdate;
    }
    
    private ArrayList<Integer> getOrderProductIdsToRemove(List<OrderProduct> source, ArrayList<Integer> persistedIds) {
        ArrayList<Integer> orderProductIdsToRemove = new ArrayList<>();
        for (Integer id : persistedIds){
            boolean founded = false;
            for (OrderProduct op : source) {
                if (op.getId() == id) founded = true;
            }
            if (!founded) orderProductIdsToRemove.add(id);
        }
        
        return orderProductIdsToRemove;
    }
    
    private Order mapOrder(ResultSet cursor) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Customer customer = new Customer();
            customer.setId(cursor.getInt("customer_id"));
            customer.setName(cursor.getString("customer_name"));
            customer.setPhone(cursor.getString("customer_phone"));
            
            Order order = new Order();
            order.setId(cursor.getInt("id"));
            order.setCustomer(customer);
            order.setCustomerId(cursor.getInt("customer_id"));
            order.setTotal(cursor.getDouble("total_price"));
            
            try {
                String closeDate = cursor.getString("close_date");
                order.setOpenDate(df.parse(cursor.getString("open_date")));
                order.setCloseDate(closeDate == null || closeDate.equals("") ? null : df.parse(closeDate));
            } catch (ParseException ex) {
                throw new JPedidosException("Falha ao converter data do pedido", ex);
            } 
            
            return order;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Order", ex);
        }
    }
    
    private Order mapOrderList(ResultSet cursor) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Customer customer = new Customer();
            customer.setId(cursor.getInt("customer_id"));
            customer.setName(cursor.getString("customer_name"));
            customer.setPhone(cursor.getString("customer_phone"));
            
            Order order = new Order();
            order.setId(cursor.getInt("id"));
            order.setCustomer(customer);
            order.setTotal(cursor.getDouble("total_price"));
            order.setStatus(cursor.getString("status"));
            
            try {
                String closeDate = cursor.getString("close_date");
                order.setOpenDate(df.parse(cursor.getString("open_date")));
                order.setCloseDate(closeDate == null || closeDate.equals("") ? null : df.parse(closeDate));
            } catch (ParseException ex) {
                throw new JPedidosException("Falha ao converter data do pedido", ex);
            } 
            
            return order;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Order list", ex);
        }
    }
    
    private OrderProduct mapOrderProductList(ResultSet cursor) {
        try {
            Product product = new Product();
            product.setId(cursor.getInt("product_id"));
            product.setName(cursor.getString("product_name"));
            
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(cursor.getInt("id"));
            orderProduct.setProduct(product);
            orderProduct.setAmount(cursor.getInt("amount"));
            return orderProduct;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Order", ex);
        }
    }
    
    private void mapCreateOrderParams(PreparedStatement preparedStatement, Order order) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setString(3, df.format(order.getOpenDate()));
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
    
    private void mapUpdateOrderParams(PreparedStatement preparedStatement, Order order) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setInt(1, order.getCustomerId());
            preparedStatement.setString(2, order.getStatusString());
            preparedStatement.setString(3, df.format(order.getCloseDate()));
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
    
    private void mapCreateOrderProdutctParams(PreparedStatement preparedStatement, OrderProduct orderProduct) {
        try {
            preparedStatement.setInt(1, orderProduct.getId());
            preparedStatement.setInt(2, orderProduct.getOrderId());
            preparedStatement.setInt(3, orderProduct.getProductId());
            preparedStatement.setInt(4, orderProduct.getAmount());
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
    
    private void mapUpdateOrderProdutctParams(PreparedStatement preparedStatement, OrderProduct orderProduct) {
        try {
            preparedStatement.setInt(1, orderProduct.getProductId());
            preparedStatement.setDouble(2, orderProduct.getAmount());
            preparedStatement.setInt(3, orderProduct.getId());
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
}
