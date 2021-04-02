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
import repositories.IProductRepository;
import repositories.IUserRepository;
import validators.ProductValidator;
import validators.UserValidator;

/**
 *
 * @author Paulo
 */
public class ProductsController extends ControllerBase<Product> {
    private IAuthContext _authContext;
    private IProductRepository _productRepository;
    private ProductValidator _productValidator;
    
    public ProductsController(
        IAuthContext authContext,
        IProductRepository productRepository,
        ProductValidator productValidator
    ) {
        super(Controller.Products, authContext, productRepository, productValidator);
        
        _productRepository = productRepository;
        _productValidator = productValidator;
        _authContext = authContext;
    }
}
