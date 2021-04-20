/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.validators;

import entities.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import utils.Result;
import validators.ProductValidator;

/**
 *
 * @author Paulo
 */
public class ProductValidatorTests {
    @ParameterizedTest
    @CsvSource({
        "       , 27 Polegadas da Dell, 1500, O nome do produto deve ser informado.",
        "     '', 27 Polegadas da Dell, 1500, O nome do produto deve ser informado.",
        "Monitor,                     , 1500, A descrição do produto deve ser informada.",
        "Monitor,                   '', 1500, A descrição do produto deve ser informada.",
        "Monitor, 27 Polegadas da Dell,   -1, O preço do produto deve ser informado.",
        "Monitor, 27 Polegadas da Dell,    0, O preço do produto deve ser informado."
    }) 
    void invalidProduct(String name, String description, double price, String expectedError) {
        ProductValidator validator = new ProductValidator();
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        
        Result result = validator.validate(product);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void validProduct() {
        ProductValidator validator = new ProductValidator();
        Product product = new Product();
        product.setName("Notebook");
        product.setDescription("I7 16GB de RAM com 120 GB de SSD");
        product.setPrice(6000);
        
        Result result = validator.validate(product);
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
}
