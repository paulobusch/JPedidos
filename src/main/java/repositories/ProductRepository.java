/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import database.DatabaseAdapter;
import entities.Product;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.SelectOption;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */


public class ProductRepository implements IProductRepository {
    private DatabaseAdapter _adapter;

    public ProductRepository(DatabaseAdapter adapter) {
        _adapter = adapter;
    }
    
    @Override
    public ArrayList<SelectOption> getAllFlat() {
        String sql = "select id, name from products where active=1";
        Connection connection = _adapter.getConnection();
        try {
            Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet result = statement.executeQuery(sql);

            ArrayList<SelectOption> options = new ArrayList<>();
            while (result.next()) {
                SelectOption option = new SelectOption();
                option.value = result.getInt("id");
                option.text = result.getString("name");
                options.add(option);
            }

            return options;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de produtos", ex);
        }
    }

    @Override
    public Product getById(int id) {
        String sql = "select * from products where id=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return mapProductDetail(result);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta do produto pelo id", ex);
        }
    }

    @Override
    public boolean hasOrders(int id) {
        String sql = "select exists(select id from orders_products where product_id=?);";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getBoolean(1);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de ordens do pedido", ex);
        }
    }

    @Override
    public ArrayList<Product> getAll() {
        String sql = "select * from products";
        Connection connection = _adapter.getConnection();
        try {
            Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            ResultSet result = statement.executeQuery(sql);
            ArrayList<Product> products = new ArrayList<Product>();
            while(result.next())
                products.add(mapProductList(result));
            
            return products;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de produtos", ex);
        }
    }

    @Override
    public void create(Product product) {
        String sqlInsert = "insert into products (name, description, price, active) values(?, ?, ?, ?);";
        String sqlLastId = "select max(id) as id from products;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            mapParams(preparedStatement, product);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum produto foi inserido");
            
            preparedStatement = connection.prepareStatement(sqlLastId,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            product.setId(result.getInt("id"));
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para inserir novo usuário", ex);
        }
    }

    @Override
    public void update(Product product) {
        String sql = "update products set name=?, description=?, price=?, active=? where id=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            mapParams(preparedStatement, product);
            preparedStatement.setInt(5, product.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum produto foi atualizado");
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para atualizar produto", ex);
        }
    }

    @Override
    public void delete(Product product) {
        String sql = "delete from products where id=?";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            preparedStatement.setInt(1, product.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum produto foi removido");
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para remover produto", ex);
        }
    }

    @Override
    public void changeActive(int id, boolean active) {
        String sql = "update products set active=? where id=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            preparedStatement.setBoolean(1, active);
            preparedStatement.setInt(2, id);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum produto foi atualizado");
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para atualizar produto", ex);
        }
    }
    
    private Product mapProductDetail(ResultSet cursor) {
        try {
            Product product = new Product();
            product.setId(cursor.getInt("id"));
            product.setName(cursor.getString("name"));
            product.setActive(cursor.getBoolean("active"));
            product.setDescription(cursor.getString("description"));
            product.setPrice(cursor.getDouble("price"));
            return product;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Product", ex);
        }
    }
    
    private Product mapProductList(ResultSet cursor) {
        try {
            Product product = new Product();
            product.setId(cursor.getInt("id"));
            product.setName(cursor.getString("name"));
            product.setActive(cursor.getBoolean("active"));
            product.setPrice(cursor.getDouble("price"));
            return product;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Product", ex);
        }
    }
    
    private void mapParams(PreparedStatement preparedStatement, Product product) {
        try {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setBoolean(4, product.isActive());
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
}
