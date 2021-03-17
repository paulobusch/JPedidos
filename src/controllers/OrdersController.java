/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import context.IAuthContext;
import entities.Order;
import enums.Controller;
import repositories.IOrderRepository;
import repositories.IRepository;
import validators.IValidator;
import validators.OrderValidator;

/**
 *
 * @author Paulo
 */
public class OrdersController extends ControllerBase<Order> {
    private IAuthContext _authContext;
    private IOrderRepository _orderRepository;
    private OrderValidator _orderValidator;
    
    public OrdersController(
        IAuthContext authContext, 
        IOrderRepository orderRepository, 
        OrderValidator orderValidator
    ) {
        super(Controller.Orders, authContext, orderRepository, orderValidator);
        
        _authContext = authContext;
        _orderRepository = orderRepository;
        _orderValidator = orderValidator;
    }
    
}
