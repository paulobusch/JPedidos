/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import entities.Order;
import entities.OrderProduct;
import entities.Product;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class OrderValidator implements IValidator<Order> {

    private OrderProductValidator _orderProductValidator;
    
    public OrderValidator(OrderProductValidator orderProductValidator) {
        _orderProductValidator = orderProductValidator;
    }
    
    public OrderProductValidator getOrderProductValidator() {
        return _orderProductValidator;
    }
    
    @Override
    public Result validate(Order order) {
        if (order.getUserId() <= 0)
            return Result.error("A operação deve ser realizada por um usuário logado.");
        
        if (order.getCustomerId() == 0 && order.getCustomer() == null)
            return Result.error("Deve ser informado o cliente.");
        
        if (order.getOrderProducts() == null || order.getOrderProducts().isEmpty())
            return Result.error("O pedido deve ter produtos.");
        
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Result productValidation = _orderProductValidator.validate(orderProduct);
            if (productValidation.hasError()) return productValidation;
        }
        
        return Result.ok();
    }
    
}
