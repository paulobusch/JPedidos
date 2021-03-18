/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import database.DatabaseAdapter;
import entities.Product;
import java.sql.Connection;
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
        String sql = "select id, name from products";
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Product> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Product entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Product entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Product entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
