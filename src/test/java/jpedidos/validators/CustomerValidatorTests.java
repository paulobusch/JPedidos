/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.validators;

import entities.Customer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.Result;
import validators.CustomerValidator;

/**
 *
 * @author Paulo
 */
public class CustomerValidatorTests {
    
    @ParameterizedTest
    @CsvSource({
        "    ,  (41) 99587-6478, O nome do cliente deve ser informado.",
        "  '',  (41) 99587-6478, O nome do cliente deve ser informado.",
        "João,                 , O telefone do cliente deve ser informado.",
        "João,               '', O telefone do cliente deve ser informado.",
        "João,    invalid phone, O telefone do cliente deve ser válido.",
        "João,      54987459856, O telefone do cliente deve ser válido.",
        "João,   (54) 987459856, O telefone do cliente deve ser válido.",
        "João,  (54) 88745-9856, O telefone do cliente deve ser válido.",
    }) 
    void invalidCustomer(String name, String phone, String expectedError) {
        CustomerValidator validator = new CustomerValidator();
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        
        Result result = validator.validate(customer);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    } 
    
    @ParameterizedTest
    @CsvSource({
        "Lucas, (41) 99587-6478",
        "Pedro, (41) 92145-3215",
        "Marcos,(55) 98596-3254"
    }) 
    void invalidCustomer(String name, String phone) {
        CustomerValidator validator = new CustomerValidator();
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        
        Result result = validator.validate(customer);
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
}
