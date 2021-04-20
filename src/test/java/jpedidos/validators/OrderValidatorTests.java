/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.validators;

import entities.Customer;
import entities.Order;
import entities.OrderProduct;
import java.util.ArrayList;
import static org.mockito.Mockito.mock;
import validators.OrderProductValidator;
import validators.OrderValidator;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.Result;
/**
 *
 * @author Paulo
 */
public class OrderValidatorTests {
        
    private OrderProductValidator _orderProductValidatorMock;
    
    public OrderValidatorTests() {
        _orderProductValidatorMock = mock(OrderProductValidator.class);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-1, 1,  true, false,  true, A operação deve ser realizada por um usuário logado.",
        " 0, 1,  true, false,  true, A operação deve ser realizada por um usuário logado.",
        " 1, 0, false, false,  true, Deve ser informado o cliente.",
        " 1, 0,  true,  true, false, O pedido deve ter produtos.",
        " 1, 1,  true, false, false, O pedido deve ter produtos.",
        " 1, 1,  true, false,  true, Order Product invalid."
    }) 
    void invalidOrder(
        int userId, 
        int customerId, 
        boolean withCustomer, 
        boolean nullOrderProducts, 
        boolean anyOrderProducts,
        String expectedError
    ) {
        OrderValidator validator = new OrderValidator(_orderProductValidatorMock);
        Order order = mock(Order.class);
        when(order.getUserId()).thenReturn(userId);
        when(order.getCustomerId()).thenReturn(customerId);
        if (withCustomer)
            when(order.getCustomer()).thenReturn(new Customer());
        if (nullOrderProducts) 
            when(order.getOrderProducts()).thenReturn(null);
        if (!nullOrderProducts && !anyOrderProducts)
            when(order.getOrderProducts()).thenReturn(new ArrayList<OrderProduct>());
        if (!nullOrderProducts && anyOrderProducts) {
            ArrayList<OrderProduct> orderProducts = new ArrayList<>();
            OrderProduct orderProduct = new OrderProduct();
            orderProducts.add(orderProduct);
            when(_orderProductValidatorMock.validate(orderProduct))
                .thenReturn(Result.error("Order Product invalid."));
            when(order.getOrderProducts()).thenReturn(orderProducts);
        }
        
        Result result = validator.validate(order);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void validOrder() {
        OrderValidator validator = new OrderValidator(_orderProductValidatorMock);
        Order order = mock(Order.class);
        when(order.getUserId()).thenReturn(2);
        when(order.getCustomerId()).thenReturn(2);
        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct();
        orderProducts.add(orderProduct);
        when(_orderProductValidatorMock.validate(orderProduct)).thenReturn(Result.ok());
        when(order.getOrderProducts()).thenReturn(orderProducts);
        
        Result result = validator.validate(order);
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
    
    @Test
    void getOrderProductValidator() {
        OrderValidator validator = new OrderValidator(_orderProductValidatorMock);
        
        OrderProductValidator result = validator.getOrderProductValidator();
        
        assertEquals(_orderProductValidatorMock, result);
    }
}
