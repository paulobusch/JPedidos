/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jpedidos.Settings;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */
public class DatabaseAdapter {
    private Connection _connection;
    
    private void connect() {
        if (isConnected()) return;
        try {
            _connection = DriverManager.getConnection(
                Settings.DbHost,
                Settings.DbUser,
                Settings.DbPass
            );
        } catch (SQLException ex) {
            throw new JPedidosException("Falha ao conectar no banco de dados", ex);
        }
    }
    
    public Connection getConnection() {
        connect();
        return _connection;
    }
    
    public void disconnect() {
        if (!isConnected()) return;
        try {
            _connection.close();
        } catch (SQLException ex) {
            throw new JPedidosException("Falha ao desconectar do banco de dados", ex);
        }
    }
    
    public void beginTransaction() {
        if (!isConnected()) 
            connect();
        try {
            _connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new JPedidosException("Falha ao iniciar transação", ex);
        }
    }
    
    public boolean commit() {
        if (!isConnected()) 
            connect();
        try {
            _connection.commit();
            _connection.setAutoCommit(true);
            return true;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao commitar alterações no banco de dados", ex);
        }
    }
    
    public void rollback() {
        if (!isConnected()) 
            connect();
        try {
            _connection.rollback();
            _connection.setAutoCommit(true);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao fazer rollback das alterações no banco de dados", ex);
        }
    }
    
    public boolean isConnected() {
        if (_connection == null) return false;
        try {
            return !_connection.isClosed();
        } catch(SQLException ex) {
            return false;
        }
    }
}
