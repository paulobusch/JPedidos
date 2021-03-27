
package Test;

import context.GenerateString;
import controllers.OrdersController;
import controllers.ProductsController;
import context.IAuthContext;
import database.DatabaseAdapter;
import entities.Order;
import static entities.Order.newORder;
import entities.OrderProduct;
import entities.Product;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class OrderProducts{
    
  
    
     @Test
        public void createProductsTest(){
        
        Product product = new Product();    
        DatabaseAdapter database = new DatabaseAdapter();
        Connection _connection;
        String generatedString = GenerateString.execute();
        
        product.name = generatedString + " testr";
        product.price = 2.5;
        product.qtd = 8;

        try{
            int productId = product.create(product);
            
            
           assertNotEquals(productId, -1);
               
        }catch(Exception e){
            System.out.println(e);
            assertEquals(1,2);
        }
      }
        
        @Test 
           public void UpdateProductsTest(){
        
            
        DatabaseAdapter database = new DatabaseAdapter();
        Connection _connection;
        Product product = new Product();  
        String generatedString = GenerateString.execute();
        
        
        product.name = generatedString + " testr";
        product.price = 2.5;
        product.qtd = 8;


        try{
            
            int productId = Product.create(product);
			product.id = productId;
			product.name = "testr";


			Product editedProduct = Product.update(product);

			assertNotNull(editedProduct);
			assertEquals(editedProduct.name, "testr");
               
        }catch(Exception e){
            System.out.println(e);
            assertEquals(1,2);
        }
}

     @Test
	public void deleteProductTest() {
		
		            
             DatabaseAdapter database = new DatabaseAdapter();
             Connection _connection;
             Product product = new Product();  
             String generatedString = GenerateString.execute();
		
		product.name = generatedString + " qwetester";
		product.price = 2.5;
		product.qtd = 8;
		

		try {
			Product.create(product);


			Product.delete(product);
			
		} catch (Exception e) {
			
			System.out.println(e);

			assertEquals(1, 2);
		}
	}

      
        
        
        
        
        
        
        
        
        
        
        
        
        
        
      
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    private void assertEquals(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertEquals(String name, String testr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertNotNull(Product editedProduct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertNotEquals(int productId, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}      
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
