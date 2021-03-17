/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import entities.Order;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class OrderValidator implements IValidator<Order> {
    private CustomerValidator _customerValidator;
    
    public OrderValidator(CustomerValidator customerValidator) {
        _customerValidator = customerValidator;
    }
    
    @Override
    public Result validate(Order order) {
        if (order.getUserId() <= 0)
            return Result.Error("A operação deve ser realizada por um usuário logado.");
        
        if (order.getCustomerId() == 0 && order.getCustomer() == null)
            return Result.Error("Deve ser informado o cliente.");
        
        Result validationCustomerResult = _customerValidator.validate(order.getCustomer());
        if (validationCustomerResult.HasError()) return validationCustomerResult;
        
        return Result.Ok();
    }
    
}
