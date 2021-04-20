/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.validators;

import entities.OrderProduct;
import entities.Product;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.Result;
import validators.OrderProductValidator;


/**
 *
 * @author Paulo
 */
public class OrderProductValidatorTests {
    @ParameterizedTest
    @CsvSource({
        "0, false,  2, O produto deve ser informado.",
        "0,  true, -1, A quantidade mínima de produtos é um.",
        "5, false, -1, A quantidade mínima de produtos é um.",
        "5,  true, -1, A quantidade mínima de produtos é um."
    })
    void invalidOrderProduct(int productId, boolean withProduct, int amount, String expectedError) {
        OrderProductValidator validator = new OrderProductValidator();
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(productId);
        orderProduct.setAmount(amount);
        if (withProduct) orderProduct.setProduct(new Product());
        
        Result result = validator.validate(orderProduct);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void validOrderProduct() {
        OrderProductValidator validator = new OrderProductValidator();
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(10);
        orderProduct.setAmount(1);
        orderProduct.setProduct(new Product());
        
        Result result = validator.validate(orderProduct);
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
}
