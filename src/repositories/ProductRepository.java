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
    
    public int id;
    public Product product;
    public int order_id;
    public int qtd;
    public String created_at;
    public String updated_at;

    private DatabaseAdapter _adapter;
    
    public ProductRepository(DatabaseAdapter adapter) {
        _adapter = adapter;
    }

    
    public static ProductRepositoryt find(int id) throws SQLException {
            ConexaoBD connect = new ConexaoBD();
           ProductRepositoryfindedProductRepository = new ProductRepository();
    
             String sql = "SELECT * FROM order_products WHERE id = ? limit 1";
      
            PreparedStatement ps = connect.conn.prepareStatement(sql);
         ps.setInt(1, id);

         ResultSet returnData = ps.executeQuery();
    
    
    if (returnData.next()) {
      findedProductRepository.id = returnData.getInt("id");
      findedProductRepository.qtd = returnData.getInt("qtd");
      findedProductRepository.product = Product.find(returnData.getInt("product_id"));
      findedProductRepository.order_id = returnData.getInt("order_id");
      findedProductRepository.created_at = returnData.getString("created_at");
      findedProductRepository.updated_at = returnData.getString("updated_at");
    }
    return findedProductRepository;
  }

    public static List<ProductRepository> list(int orderId) throws SQLException {
    ConexaoBD connect = new ConexaoBD();
    
    String sql = "SELECT * FROM Product_Repository where order_id = ?";
      
    PreparedStatement ps = connect.conn.prepareStatement(sql);
    ps.setInt(1, orderId);

    ResultSet returnData = ps.executeQuery();
    
    List<ProductRepository> findedProductRepository = new ArrayList<ProductRepository>();
    while (returnData.next()) {
      ProductRepository findedProductRepository = new ProductRepository();

      findedProductRepository.id = returnData.getInt("id");
      findedProductRepository.qtd = returnData.getInt("qtd");
      findedProductRepository.product = Product.find(returnData.getInt("product_id"));
      findedProductRepository.order_id = returnData.getInt("order_id");
      findedProductRepository.created_at = returnData.getString("created_at");
      findedProductRepository.updated_at = returnData.getString("updated_at");

      findedProductRepository.add(findedProductRepository);
    }
    return findedProductRepository;
  }
    
    
  public static void create(ProductRepository ProductRepositoryToCreate) throws SQLException {
        ConexaoBD connect = new ConexaoBD();
   
        CurrentDateInString currentDateInString = new CurrentDateInString();
   
            String sql = "INSERT INTO order_products (product_id, order_id, qtd, created_at) values (?,?,?,?)";
     
                PreparedStatement ps = connect.conn.prepareStatement(sql);
                ps.setInt(1, ProductRepositoryToCreate.product.id);
                ps.setInt(2, ProductRepositoryToCreate.order_id);
                ps.setInt(3, ProductRepositoryToCreate.qtd);
                ps.setString(4, currentDateInString.execute());

            ps.executeUpdate();

  }

    
    public static void update(ProductRepository ProductRepositoryToUpdate) throws SQLException {
        ConexaoBD connect = new ConexaoBD();
   
            CurrentDateInString currentDateInString = new CurrentDateInString();
   
                    String sql = "UPDATE Product_Repository SET product_id=?, order_id=?, qtd=?, updated_at=? WHERE id=?";
     
                            PreparedStatement ps = connect.conn.prepareStatement(sql);
                            ps.setInt(1, ProductRepositoryToUpdate.product.id);
                            ps.setInt(2, ProductRepositoryToUpdate.order_id);
                            ps.setInt(3, ProductRepositoryToUpdate.qtd);
                            ps.setString(4, currentDateInString.execute());
                            ps.setInt(5, ProductRepositoryToUpdate.id);

                System.out.println("before execute");
                            ps.executeUpdate();
                System.out.println("after execute");

  }

    
    public void delete(ProductRepository ProductRepositoryToDelete) throws SQLException {
            ConexaoBD connect = new ConexaoBD();
    
                String sql = "DELETE FROM Product_Repository WHERE id = ?";
     
                PreparedStatement ps = connect.conn.prepareStatement(sql);
                ps.setInt(1, ProductRepositoryToDelete.id);
  
        ps.executeUpdate();
    }
    

}
