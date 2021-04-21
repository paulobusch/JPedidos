/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.entities;


import entities.Order;
import entities.OrderProduct;
import entities.Product;
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
public class OrderProductTests {
    @Test
    void computeTotalWithoutProduct() {
        OrderProduct orderProduct = new OrderProduct();

        Exception exception = assertThrows(
            JPedidosException.class, 
            () -> orderProduct.computeTotal()
        );
       
        assertEquals("O produto deve ser carregado para cálculo do preço!", exception.getMessage());
    }
    
    @Test
    void computeTotalWithSuccess() {
        Product product = new Product();
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        product.setPrice(10);
        orderProduct.setAmount(2);
        
        double total = orderProduct.computeTotal();
        
        assertEquals(20, total);
    }
    
    @Test
    void addAmount() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setAmount(0);
        
        orderProduct.addAmount(2);
        orderProduct.addAmount(3);
        
        assertEquals(5, orderProduct.getAmount());
    }
    
    @Test
    void setRawAmountWithNumber() {
        OrderProduct orderProduct = new OrderProduct();
        
        orderProduct.setAmount("123");
        
        assertEquals(123, orderProduct.getAmount());
    }
    
    @Test
    void setRawAmountWithText() {
        OrderProduct orderProduct = new OrderProduct();
        
        orderProduct.setAmount("test");
        
        assertEquals(0, orderProduct.getAmount());
    }
    
    @Test
    void setProductWithoutValue() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(1);
        
        orderProduct.setProduct(null);
        
        assertNull(orderProduct.getProduct());
        assertEquals(0, orderProduct.getProductId());
    }
    
    @Test
    void setProductWithValue() {
        Product product = new Product();
        OrderProduct orderProduct = new OrderProduct();
        product.setId(5);
        
        orderProduct.setProduct(product);
        
        assertEquals(product, orderProduct.getProduct());
        assertEquals(5, orderProduct.getProductId());
    }
    
    @Test
    void setOrderWithoutValue() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderId(1);
        
        orderProduct.setOrder(null);
        
        assertNull(orderProduct.getOrder());
        assertEquals(0, orderProduct.getOrderId());
    }
    
    @Test
    void setOrderWithValue() {
        Order order = new Order();
        OrderProduct orderProduct = new OrderProduct();
        order.setId(7);
        
        orderProduct.setOrder(order);
        
        assertEquals(order, orderProduct.getOrder());
        assertEquals(7, orderProduct.getOrderId());
    }
    
    @Test
    void gettersAndSetters() {
        OrderProduct orderProduct = new OrderProduct();
        
        orderProduct.setOrderId(2);
        orderProduct.setProductId(3);
        
        assertEquals(2, orderProduct.getOrderId());
        assertEquals(3, orderProduct.getProductId());
    }
}
