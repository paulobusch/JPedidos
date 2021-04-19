/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.controllers;

import context.IAuthContext;
import controllers.OrdersController;
import controllers.ProductsController;
import static org.mockito.Mockito.mock;
import repositories.IProductRepository;
import validators.ProductValidator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Paulo
 */
public class ProductsControllerTests {
    private IAuthContext _authContextMock;
    private IProductRepository _productRepositoryMock;
    private ProductValidator _productValidatorMock;
    
    public ProductsControllerTests() {
        _authContextMock = mock(IAuthContext.class);
        _productRepositoryMock = mock(IProductRepository.class);
        _productValidatorMock = mock(ProductValidator.class);
    }
    
    @Test 
    void constructor() {
        ProductsController productsController = getProductsController();
    }
    
    private ProductsController getProductsController(){
        return new ProductsController(
            _authContextMock, 
            _productRepositoryMock, 
            _productValidatorMock
        );
    }
}
