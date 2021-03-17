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
    private Connection connection;
    
    private void connect() {
        if (isConnected()) return;
        try {
            connection = DriverManager.getConnection(
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
        return connection;
    }
    
    public void disconnect() {
        if (!isConnected()) return;
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new JPedidosException("Falha ao desconectar do banco de dados", ex);
        }
    }
    
    public boolean commit() {
        if (!isConnected()) 
            connect();
        try {
            connection.commit();
            return true;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao commitar alterações no banco de dados", ex);
        }
    }
    
    public void rollback() {
        if (!isConnected()) 
            connect();
        try {
            connection.rollback();
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao fazer rollback das alterações no banco de dados", ex);
        }
    }
    
    public boolean isConnected() {
        if (connection == null) return false;
        try {
            return !connection.isClosed();
        } catch(SQLException ex) {
            return false;
        }
    }
}
