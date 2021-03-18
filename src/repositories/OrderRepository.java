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
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public Order getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Order> getAll() {
        String sql = "select o.id, o.customer_id, c.name as customer_name, sum(p.price) as total_price, o.date from orders o\n" +
            "join customers c on c.id=o.customer_id\n" +
            "join orders_products op on op.order_id=o.id\n" +
            "join products p on p.id=op.product_id"; 
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
            throw new JPedidosException("Falha na execução da consulta de usuário pelo id", ex);
        }
    }

    @Override
    public void create(Order order) {
        String sqlInsert = "insert into orders (user_id, customer_id, date) values(?, ?, ?);";
        String sqlLastId = "select max(id) as id from orders;";
        Connection connection = _adapter.getConnection();
        try {
            _adapter.beginTransaction();
            
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            mapOrderParams(preparedStatement, order);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum pedido foi inserido");
            
            preparedStatement = connection.prepareStatement(sqlLastId,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            order.setId(result.getInt("id"));
            
            String sqlOrderProductInsert = "insert into orders_products (order_id, product_id, amount) values(?, ?, ?);";
            String sqlOrderProductLastId = "select max(id) as id from orders_products;";
            
            preparedStatement = connection.prepareStatement(sqlOrderProductLastId,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            result = preparedStatement.executeQuery();
            result.next();
            int lastIdOrderProduct =result.getInt("id");
            
            String sqlBatchOrderProductInsert = "";
            for (int i = 0; i < order.getOrderProducts().size(); i ++)
                sqlBatchOrderProductInsert += sqlOrderProductInsert;
            preparedStatement = connection.prepareStatement(sqlBatchOrderProductInsert,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            int offset = 0;
            List<OrderProduct> orderProducts = order.getOrderProducts();
            for (OrderProduct op : orderProducts) {
                op.setId(orderProducts.indexOf(op) + lastIdOrderProduct + 1);
                op.setOrderId(order.getId());
                mapOrderProdutctParams(preparedStatement, op, offset);
                offset += 3;
            }
            
            rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum produto do pedido foi inserido");
            
            _adapter.commit();
        } catch(SQLException ex) {
            _adapter.rollback();
            throw new JPedidosException("Falha na execução do comando para inserir novo usuário", ex);
        }
    }

    @Override
    public void update(Order entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Order entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Order mapOrderList(ResultSet cursor) {
        try {
            Customer customer = new Customer();
            customer.setId(cursor.getInt("customer_id"));
            customer.setName(cursor.getString("customer_name"));
            
            Order order = new Order();
            order.setId(cursor.getInt("id"));
            order.setCustomer(customer);
            order.setTotal(cursor.getDouble("total_price"));
            order.setDate(cursor.getDate("date"));
            return order;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Order list", ex);
        }
    }
    
    private void mapOrderParams(PreparedStatement preparedStatement, Order order) {
        try {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setDate(3, new java.sql.Date(order.getDate().getTime()));
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
    
    private void mapOrderProdutctParams(PreparedStatement preparedStatement, OrderProduct orderProduct, int offset) {
        try {
            preparedStatement.setInt(1 + offset, orderProduct.getOrderId());
            preparedStatement.setInt(2 + offset, orderProduct.getProductId());
            preparedStatement.setInt(3 + offset, orderProduct.getAmount());
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
}
