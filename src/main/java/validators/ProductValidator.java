/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import entities.Order;
import entities.Product;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class ProductValidator implements IValidator<Product> {

    @Override
    public Result validate(Product product) {
        if (product.getName() == null || product.getName().equals(""))
            return Result.Error("O nome deve ser informado.");
        if (product.getDescription()== null || product.getDescription().equals(""))
            return Result.Error("A descrição deve ser informada.");
        if (product.getPrice() > 0)
            return Result.Error("A quantidade de produtos deve ser maior que um.");
        return Result.Ok();
    }
    
}
