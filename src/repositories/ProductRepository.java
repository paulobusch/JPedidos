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
public class ProductRepository {
  public int id;
  public String name;
  public String description;
  public double price;
  public int qtd;
  public String created_at;
  public String updated_at;


  public static ProductRepository find(int id) throws SQLException {
        ConexaoBD connect = new ConexaoBD();
        ProductRepository findedProductRepository = newProductRepository();
    
     String sql = "SELECT * FROM Product_Repository WHERE id = ? limit 1";
      
    PreparedStatement ps = connect.conn.prepareStatement(sql);
    ps.setInt(1, id);

    ResultSet returnData = ps.executeQuery();
    
    
    if (returnData.next()) {
      findedProductRepository.id = returnData.getInt("id");
      findedProductRepository.name = returnData.getString("name");
      findedProductRepository.description = returnData.getString("description");
      findedProductRepository.price = returnData.getDouble("price");
      findedProductRepository.qtd = returnData.getInt("qtd");
      findedProductRepository.created_at = returnData.getString("created_at");
      findedProductRepository.updated_at = returnData.getString("updated_at");
    }
    return findedProductRepository;
  }

  public static List<ProductRepository> list() throws SQLException {
    ConexaoBD connect = new ConexaoBD();
    
    String sql = "SELECT * FROM Product_Repository";
      
    PreparedStatement ps = connect.conn.prepareStatement(sql);

    ResultSet returnData = ps.executeQuery();
    
        List<ProductRepository> findedProductRepository = new ArrayList<ProductRepository>();
             while (returnData.next()) {
            ProductRepository findedProductRepository = new ProductRepository();

      findedProductRepository.id = returnData.getInt("id");
      findedProductRepository.name = returnData.getString("name");
      findedProductRepository.description = returnData.getString("description");
      findedPProductRepository.price = returnData.getDouble("price");
      findedProductRepository.qtd = returnData.getInt("qtd");
      findedProductRepository.created_at = returnData.getString("created_at");
      findedProductRepository.updated_at = returnData.getString("updated_at");
      findedProductRepository.add(findedProduct);
    }
    return findedProductRepository;
  }

  public static void create(ProductRepository ProductRepositoryToCreate) throws SQLException {
   ConexaoBD connect = new ConexaoBD();
   
   CurrentDateInString currentDateInString = new CurrentDateInString();
   
   String sql = "INSERT INTO Product_Repository (name, description, price, qtd, created_at) values (?,?,?,?,?)";
     
   PreparedStatement ps = connect.conn.prepareStatement(sql);
   ps.setString(1, ProductRepositoryToCreate.name);
   ps.setString(2, ProductRepositoryToCreate.description);
   ps.setDouble(3, ProductRepositoryToCreate.price);
   ps.setInt(4, ProductRepositoryToCreate.qtd);
   ps.setString(5, currentDateInString.execute());

   ps.executeUpdate();

  }

  public static void update(ProductRepository ProductRepositoryToUpdate) throws SQLException {
   ConexaoBD connect = new ConexaoBD();
   
   CurrentDateInString currentDateInString = new CurrentDateInString();
   
   String sql = "UPDATE Product_Repository SET name=?, description=?, price=?, qtd=?, updated_at=? WHERE id=?";
     
   PreparedStatement ps = connect.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
   ps.setString(1, ProductRepositoryToUpdate.name);
   ps.setString(2, ProductRepositoryToUpdate.description);
   ps.setDouble(3, ProductRepositoryToUpdate.price);
   ps.setInt(4, ProductRepositoryToUpdate.qtd);
   ps.setString(5, currentDateInString.execute());
   ps.setInt(6, ProductRepositoryToUpdate.id);

   ps.executeUpdate();

   ResultSet returnData = ps.getGeneratedKeys();
   
   if (returnData.next()) {
     System.out.println(returnData.getInt(1));
   }
  }

  public void delete(ProductRepository ProductRepositoryToDelete) throws SQLException {
    ConexaoBD connect = new ConexaoBD();
    
    String sql = "DELETE FROM Product_Repository WHERE id = ?";
     
    PreparedStatement ps = connect.conn.prepareStatement(sql);
    ps.setInt(1, ProductRepositoryToDelete.id);
  
    ps.executeUpdate();
  }
    

}
