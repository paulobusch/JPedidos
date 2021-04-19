/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.controllers;

import context.IAuthContext;
import controllers.ControllerBase;
import controllers.OrdersController;
import entities.Order;
import entities.Product;
import entities.User;
import enums.Controller;
import enums.CrudFunctionality;
import java.util.ArrayList;
import jpedidos.mocks.EntityMock;
import models.SelectOption;
import repositories.ICustomerRepository;
import repositories.IOrderRepository;
import repositories.IProductRepository;
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
public class OrdersControllerTests {    
    private IAuthContext _authContextMock;
    private IOrderRepository _orderRepositoryMock;
    private ICustomerRepository _customerRepositoryMock;
    private IProductRepository _productRepositoryMock;
    private OrderValidator _orderValidatorMock;
    
    public OrdersControllerTests() {
        _authContextMock = mock(IAuthContext.class);
        _orderRepositoryMock = mock(IOrderRepository.class);
        _customerRepositoryMock = mock(ICustomerRepository.class);
        _productRepositoryMock = mock(IProductRepository.class);
        _orderValidatorMock = mock(OrderValidator.class);
    }
    
    @Test
    void getValidatorTest() {
        OrdersController ordersController = getOrdersController();
        OrderValidator validator = ordersController.getValidator();
        
        assertEquals(_orderValidatorMock, validator);
    }
    
    @Test
    void createWithoutAuthentication() {
        User user = new User();
        Order orderMock = mock(Order.class);
        user.setId(5);
        when(orderMock.getId())
            .thenReturn(6);
        when(_authContextMock.getCurrentUser())
            .thenReturn(user);
        when(_authContextMock.isAuthenticated())
            .thenReturn(false);
        when(_authContextMock.hasPermission(Controller.Orders, CrudFunctionality.Create))
            .thenReturn(false);
        when(_orderValidatorMock.validate(orderMock))
            .thenReturn(Result.ok());
        OrdersController ordersController = getOrdersController();
        
        Result result = ordersController.create(orderMock);
        
        assertTrue(result.hasError());
        assertEquals("O Usuário não tem permissão para criar um novo registro", result.getErrorMessage());
        verify(_orderRepositoryMock, never()).create(any());
    }
    
    @Test
    void create() {
        User user = new User();
        Order orderMock = mock(Order.class);
        user.setId(5);
        when(orderMock.getId())
            .thenReturn(6);
        when(_authContextMock.getCurrentUser())
            .thenReturn(user);
        when(_authContextMock.isAuthenticated())
            .thenReturn(true);
        when(_authContextMock.hasPermission(Controller.Orders, CrudFunctionality.Create))
            .thenReturn(true);
        when(_orderValidatorMock.validate(orderMock))
            .thenReturn(Result.ok());
        OrdersController ordersController = getOrdersController();
        
        Result result = ordersController.create(orderMock);
        
        assertFalse(result.hasError());
        verify(_orderRepositoryMock, atLeastOnce()).create(orderMock);
        verify(orderMock, atLeastOnce()).setUserId(user.getId());
    }
    
    @Test
    void updateWithoutAuthentication() {
        User user = new User();
        Order orderMock = mock(Order.class);
        user.setId(5);
        when(orderMock.getId())
            .thenReturn(6);
        when(_authContextMock.getCurrentUser())
            .thenReturn(user);
        when(_authContextMock.isAuthenticated())
            .thenReturn(false);
        when(_authContextMock.hasPermission(Controller.Orders, CrudFunctionality.Update))
            .thenReturn(false);
        when(_orderValidatorMock.validate(orderMock))
            .thenReturn(Result.ok());
        OrdersController ordersController = getOrdersController();
        
        Result result = ordersController.update(orderMock);
        
        assertTrue(result.hasError());
        assertEquals("O Usuário não tem permissão para atualizar o registro", result.getErrorMessage());
        verify(_orderRepositoryMock, never()).create(any());
    }
    
    @Test
    void update() {
        User user = new User();
        Order orderMock = mock(Order.class);
        user.setId(5);
        when(orderMock.getId())
            .thenReturn(6);
        when(_authContextMock.getCurrentUser())
            .thenReturn(user);
        when(_authContextMock.isAuthenticated())
            .thenReturn(true);
        when(_authContextMock.hasPermission(Controller.Orders, CrudFunctionality.Update))
            .thenReturn(true);
        when(_orderValidatorMock.validate(orderMock))
            .thenReturn(Result.ok());
        OrdersController ordersController = getOrdersController();
        
        Result result = ordersController.update(orderMock);
        
        assertFalse(result.hasError());
        verify(_orderRepositoryMock, atLeastOnce()).update(orderMock);
        verify(orderMock, atLeastOnce()).setUserId(user.getId());
    }
    
    @Test
    void getTotalPrice() {
        int orderId = 5;
        double expectedTotal = 150.5;
        when(_orderRepositoryMock.getTotalPrice(orderId))
            .thenReturn(expectedTotal);
        OrdersController ordersController = getOrdersController();

        double total = ordersController.getTotalPrice(orderId);
        
        assertEquals(expectedTotal, total);
        verify(_orderRepositoryMock, atLeastOnce()).getTotalPrice(orderId);
    }
    
    @Test
    void getCustomerFlat() {
        ArrayList<SelectOption> expectedResult = new ArrayList<>();
        when(_customerRepositoryMock.getAllFlat())
            .thenReturn(expectedResult);
        OrdersController ordersController = getOrdersController();

        ArrayList<SelectOption> result = ordersController.getCustomersFlat();
        
        assertEquals(expectedResult, result);
        verify(_customerRepositoryMock, atLeastOnce()).getAllFlat();
    }
    
    @Test
    void getProductsFlat() {
        ArrayList<SelectOption> expectedResult = new ArrayList<>();
        when(_productRepositoryMock.getAllFlat())
            .thenReturn(expectedResult);
        OrdersController ordersController = getOrdersController();

        ArrayList<SelectOption> result = ordersController.getProductsFlat();
        
        assertEquals(expectedResult, result);
        verify(_productRepositoryMock, atLeastOnce()).getAllFlat();
    }
    
    @Test
    void getProduct() {
        Product expectedResult = new Product();
        expectedResult.setId(10);
        when(_productRepositoryMock.getById(expectedResult.getId()))
            .thenReturn(expectedResult);
        OrdersController ordersController = getOrdersController();

        Product result = ordersController.getProduct(expectedResult.getId());
        
        assertEquals(expectedResult, result);
        verify(_productRepositoryMock, atLeastOnce()).getById(expectedResult.getId());
    }
    
    private OrdersController getOrdersController(){
        return new OrdersController(
            _authContextMock, 
            _orderRepositoryMock, 
            _customerRepositoryMock, 
            _productRepositoryMock, 
            _orderValidatorMock
        );
    }
}
