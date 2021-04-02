/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import entities.Customer;
import utils.Result;
import utils.Validator;

/**
 *
 * @author Paulo
 */
public class CustomerValidator implements IValidator<Customer> {

    @Override
    public Result validate(Customer customer) {
        if (customer.getName() == null || customer.getName().equals(""))
            return Result.error("O nome do cliente deve ser informado.");
        
        if (customer.getPhone()== null || customer.getPhone().equals(""))
            return Result.error("O telefone do cliente deve ser informado.");
        
        if (!Validator.validatePhone(customer.getPhone()))
            return Result.error("O telefone do cliente deve ser válido.");
        
        return Result.ok();
    }
    
}
