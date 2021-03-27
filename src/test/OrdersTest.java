
package Test;

import context.GenerateString;
import controllers.OrdersController;
import context.IAuthContext;
import database.DatabaseAdapter;
import entities.Order;
import static entities.Order.newORder;
import entities.OrderProduct;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class OrdersTest{
    
  @Test
        public void createOrderTest(){
        
            
        DatabaseAdapter database = new DatabaseAdapter();
        Connection _connection;
        Order order = new Order();
        String generatedString = GenerateString.execute();
        MainMigration mainMigration = new MainMigration();
        

        try{
             
            String user = generatedString + "test";
               
               Order newOrder = order.create(order);
            
               assertNotNull(newOrder);
               assertNotEquals(newOrder.userId, -1);
               
        }catch(Exception e){
            System.out.println(e);
            assertEquals(1,2);
        }
      }
        
        @Test 
           public void UpdateOrderTest(){
        
            
        DatabaseAdapter database = new DatabaseAdapter();
        Connection _connection;
        Order order = new Order();
        String generatedString = GenerateString.execute();
       
        

        try{
             
            String user = generatedString + "test";
            
               Order.newORder = Order.create(order);
               order.id = newORder.id;
               Order editedOrder = order.updateProduct(order);
            
               assertNotNull(editedOrder);
               assertNotEquals(editedOrder.userId, -1);
               
        }catch(Exception e){
            System.out.println(e);
            assertEquals(1,2);
        }
}
           
           

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    private void assertEquals(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertNotNull(Order newOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertNotEquals(int id, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}