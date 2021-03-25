/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import context.IAuthContext;
import entities.Order;
import entities.Product;
import enums.Controller;
import enums.CrudFunctionality;
import java.util.ArrayList;
import models.SelectOption;
import repositories.CustomerRepository;
import repositories.ICustomerRepository;
import repositories.IOrderRepository;
import repositories.IProductRepository;
import repositories.IRepository;
import utils.Result;
import utils.ResultData;
import validators.IValidator;
import validators.OrderValidator;

/**
 *
 * @author Paulo
 */
public class OrdersController extends ControllerBase<Order> {
    private IAuthContext _authContext;
    private IOrderRepository _orderRepository;
    private ICustomerRepository _customerRepository;
    private IProductRepository _productRepository;
    private OrderValidator _orderValidator;
    
    public OrdersController(
        IAuthContext authContext, 
        IOrderRepository orderRepository, 
        ICustomerRepository customerRepository, 
        IProductRepository productRepository,
        OrderValidator orderValidator
    ) {
        super(Controller.Orders, authContext, orderRepository, orderValidator);
        
        _authContext = authContext;
        _orderRepository = orderRepository;
        _customerRepository = customerRepository;
        _productRepository = productRepository;
        _orderValidator = orderValidator;
    }
    
    public OrderValidator getValidator() {
        return _orderValidator;
    }
    
    @Override
    public Result create(Order order) {
        if (_authContext.isAuthenticated()) {
            int userId = _authContext.getCurrentUser().getId();
            order.setUserId(userId);
        }
        
        return super.create(order);
    }
    
    @Override
    public Result update(Order order) {
        if (_authContext.isAuthenticated()) {
            int userId = _authContext.getCurrentUser().getId();
            order.setUserId(userId);
        }
        
        return super.update(order);
    }
    
    public double getTotalPrice(int id) {
        return _orderRepository.getTotalPrice(id);
    }
    
    public ArrayList<SelectOption> getCustomersFlat() {
        return _customerRepository.getAllFlat();
    }
    
    public ArrayList<SelectOption> getProductsFlat() {
        return _productRepository.getAllFlat();
    }
    
    public Product getProduct(int id) {
        return _productRepository.getById(id);
    }
}
