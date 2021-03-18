/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import entities.Customer;
import entities.OrderProduct;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class OrderProductValidator implements IValidator<OrderProduct> {

    @Override
    public Result validate(OrderProduct orderProduct) {
        if (orderProduct.getProductId() == 0 && orderProduct.getProduct() == null)
            return Result.error("O producto deve ser informado.");
        if (orderProduct.getOrderId()== 0 && orderProduct.getOrder()== null)
            return Result.error("O pedido deve ser informado.");
        if (orderProduct.getAmount() < 1)
            return Result.error("A quantidade ménima de produtos é um.");
        
        return Result.ok();
    }
    
}
