/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.entities;

import entities.Customer;
import entities.Order;
import entities.OrderProduct;
import entities.Product;
import entities.User;
import enums.OrderStatus;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */
public class OrderTests {
    @Test
    void computeTotalWithoutProducts() {
        Order order = new Order();
        
        double total = order.computeTotal();
        
        assertEquals(0, total);
    }
    
    @Test
    void computeTotalWithProducts() {
        Order order = new Order();
        Product product1 = new Product();
        Product product2 = new Product();
        OrderProduct orderProduct1 = new OrderProduct();
        OrderProduct orderProduct2 = new OrderProduct();
        
        order.addOrderProduct(orderProduct1);
        order.addOrderProduct(orderProduct2);
        orderProduct1.setProduct(product1);
        orderProduct2.setProduct(product2);
        orderProduct1.setAmount(1);
        orderProduct2.setAmount(2);
        product1.setPrice(15);
        product2.setPrice(30);
        
        double total = order.computeTotal();
        
        assertEquals(75, total);
    }
    
    @Test
    void addOrderProduct() {
        Order order = new Order();
        OrderProduct orderProduct1 = new OrderProduct();
        OrderProduct orderProduct2 = new OrderProduct();
        OrderProduct orderProduct3 = new OrderProduct();
        orderProduct1.setId(2);
        orderProduct2.setId(1);
        
        order.addOrderProduct(orderProduct1);
        order.addOrderProduct(orderProduct2);
        order.addOrderProduct(orderProduct3);
        
        assertEquals(3, order.getOrderProducts().size());
    }
    
    @Test
    void updateProductNotFound() {
        Order order = new Order();
        OrderProduct orderProduct = new OrderProduct();

        Exception exception = assertThrows(
            JPedidosException.class, 
            () -> order.updateProduct(orderProduct)
        );
       
        assertEquals("O produto deve existir no pedido para ser atualizado!", exception.getMessage());
    }
    
    @Test
    void updateProductWithSuccess() {
        Order order = new Order();
        OrderProduct originalOrderProduct = new OrderProduct();
        order.addOrderProduct(originalOrderProduct);
        originalOrderProduct.setProductId(5);
        originalOrderProduct.setAmount(2);
        
        OrderProduct updateOrderProduct = new OrderProduct();
        updateOrderProduct.setId(originalOrderProduct.getId());
        updateOrderProduct.setProductId(10);
        updateOrderProduct.setAmount(3);
        
        order.updateProduct(updateOrderProduct);
        
        assertEquals(updateOrderProduct.getId(), originalOrderProduct.getId());
        assertEquals(10, originalOrderProduct.getProductId());
        assertEquals(3, originalOrderProduct.getAmount());
    }
    
    @Test
    void removeProductNotFound() {
        Order order = new Order();
        OrderProduct orderProduct1 = new OrderProduct();
        OrderProduct orderProduct2 = new OrderProduct();
                
        order.addOrderProduct(orderProduct2);

        Exception exception = assertThrows(
            JPedidosException.class, 
            () -> order.removeProduct(orderProduct1)
        );
       
        assertEquals("O produto deve existir no pedido para ser removido!", exception.getMessage());
    }
    
    @Test
    void removeProductWithSuccess() {
        Order order = new Order();
        OrderProduct orderProduct1 = new OrderProduct();
        OrderProduct orderProduct2 = new OrderProduct();
        
        order.addOrderProduct(orderProduct1);
        order.addOrderProduct(orderProduct2);
        
        order.removeProduct(orderProduct1);
        
        assertEquals(1, order.getOrderProducts().size());
        assertEquals(orderProduct2, order.getOrderProducts().get(0));
    }
    
    @Test
    void gettersAndSetters() {
        Date expectedOpenDate = new Date(2021, 04, 21);
        Date expectedCloseDate = new Date(2021, 04, 22);
        
        Order order = new Order();
        
        order.setUserId(1);
        order.setCustomerId(2);
        order.setTotal(3);
        order.setOpenDate(expectedOpenDate);
        order.setCloseDate(expectedCloseDate);
        order.setStatus(OrderStatus.Open);
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(1, order.getUserId());
        assertEquals(2, order.getCustomerId());
        assertEquals(3, order.getTotal());
        assertEquals(
            df.format(expectedOpenDate),
            df.format(order.getOpenDate())
        );
        assertEquals(
            df.format(expectedCloseDate),
            df.format(order.getCloseDate())
        );
        assertEquals(OrderStatus.Open, order.getStatus());
    }
    
    @ParameterizedTest
    @CsvSource({
        "aberto, Open",
        "fechado, Close",
        "invalid, ",
    })
    void setRawStatus(String statusString, OrderStatus expectedStatus) {
        Order order = new Order();
        
        order.setStatus(statusString);
        
        assertEquals(expectedStatus, order.getStatus());
    }
    
    @ParameterizedTest
    @CsvSource({
        "Open, aberto",
        "Close, fechado",
        "     , "
    })
    void getRawStatus(OrderStatus status, String expectedStatus) {
        Order order = new Order();
        
        order.setStatus(status);
        
        assertEquals(expectedStatus, order.getStatusString());
    }
    
    @Test
    void setCustomerWithoutValue() {
        Order order = new Order();
        order.setCustomerId(1);
        
        order.setCustomer(null);
        
        assertNull(order.getCustomer());
        assertEquals(0, order.getCustomerId());
    }
    
    @Test
    void setCustomerWithValue() {
        Order order = new Order();
        Customer customer = new Customer();
        customer.setId(7);
        
        order.setCustomer(customer);
        
        assertEquals(customer, order.getCustomer());
        assertEquals(7, order.getCustomerId());
    }
    
    @Test
    void setUserWithoutValue() {
        Order order = new Order();
        order.setUserId(1);
        
        order.setUser(null);
        
        assertNull(order.getUser());
        assertEquals(0, order.getUserId());
    }
    
    @Test
    void setUserWithValue() {
        Order order = new Order();
        User user = new User();
        user.setId(7);
        
        order.setUser(user);
        
        assertEquals(user, order.getUser());
        assertEquals(7, order.getUserId());
    }
}
