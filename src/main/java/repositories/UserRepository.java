/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import database.DatabaseAdapter;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */
public class UserRepository implements IUserRepository {

    private DatabaseAdapter _adapter;
    
    public UserRepository(DatabaseAdapter adapter) {
        _adapter = adapter;
    }
    
    @Override
    public boolean existByLogin(String login) {
        String sql = "select exists(select id from users where login=?);";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setString(1, login);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getBoolean(1);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de usuário pelo login", ex);
        }
    }

    @Override
    public boolean existByLogin(String login, int id) {
        String sql = "select exists(select id from users where id!=? and login=?);";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, login);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getBoolean(1);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de usuário pelo login com id", ex);
        }
    }

    @Override
    public boolean existByEmail(String email, int id) {
        String sql = "select exists(select id from users where id!=? and email=?);";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, email);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getBoolean(1);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de usuário pelo email", ex);
        }
    }

    @Override
    public User getByLogin(String login) {
        String sql = "select * from users where login=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setString(1, login);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return mapUser(result);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de usuário pelo login", ex);
        }
    }

    @Override
    public User getById(int id) {
        String sql = "select * from users where id=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            preparedStatement.setInt(1, id);
            
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return mapUser(result);
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de usuário pelo id", ex);
        }
    }

    @Override
    public ArrayList<User> getAll() {
        String sql = "select * from users";
        Connection connection = _adapter.getConnection();
        try {
            Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            ResultSet result = statement.executeQuery(sql);
            ArrayList<User> users = new ArrayList<User>();
            while(result.next())
                users.add(mapUser(result));
            
            return users;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução da consulta de usuários", ex);
        }
    }

    @Override
    public void changePassword(int id, String password, boolean isTemporary) {
        String sql = "update users set password=?, password_temporary=? where id=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            preparedStatement.setString(1, password);
            preparedStatement.setBoolean(2, isTemporary);            
            preparedStatement.setInt(3, id);
            
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum usuário foi atualizado");
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para atualizar a senha do usuário", ex);
        }
    }

    @Override
    public void create(User user) {
        String sqlInsert = "insert into users (name, login, password, email, role) values(?, ?, ?, ?, ?);";
        String sqlLastId = "select max(id) as id from users;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            mapParamsCreate(preparedStatement, user);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum usuário foi inserido");
            
            preparedStatement = connection.prepareStatement(sqlLastId,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            user.setId(result.getInt("id"));
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para inserir novo usuário", ex);
        }
    }

    @Override
    public void update(User user) {
        String sql = "update users set name=?, login=?, email=?, role=? where id=?;";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            mapParamsUpdate(preparedStatement, user);
            preparedStatement.setInt(5, user.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum usuário foi atualizado");
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para atualizar usuário", ex);
        }
    }

    @Override
    public void delete(User user) {
        String sql = "delete from users where id=?";
        Connection connection = _adapter.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );
            
            preparedStatement.setInt(1, user.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new JPedidosException("Nenhum usuário foi removido");
        } catch(SQLException ex) {
            throw new JPedidosException("Falha na execução do comando para remover usuário", ex);
        }
    }
    
    private User mapUser(ResultSet cursor) {
        try {
            User user = new User();
            user.setId(cursor.getInt("id"));
            user.setName(cursor.getString("name"));
            user.setEmail(cursor.getString("email"));
            user.setLogin(cursor.getString("login"));
            user.setPasswordTemporary(cursor.getBoolean("password_temporary"));
            user.setPassword(cursor.getString("password"));
            user.setRole(cursor.getString("role"));
            return user;
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear User", ex);
        }
    }
    
    private void mapParamsCreate(PreparedStatement preparedStatement, User user) {
        try {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getRoleString());
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
    
    private void mapParamsUpdate(PreparedStatement preparedStatement, User user) {
        try {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRoleString());
        } catch(SQLException ex) {
            throw new JPedidosException("Falha ao mapear Params", ex);
        }
    }
}
