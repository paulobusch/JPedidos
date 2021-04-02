/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import database.DatabaseAdapter;
import entities.Customer;
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
public class CustomerRepository implements ICustomerRepository {

    private DatabaseAdapter _adapter;
    
    public CustomerRepository(DatabaseAdapter adapter) {
        _adapter = adapter;
    }

    @Override
    public ArrayList<SelectOption> getAllFlat() {
        String sql = "select id, name from customers";
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
    public Customer getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Customer> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Customer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
