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
            return Result.error("O nome do produto deve ser informado.");
        if (product.getDescription()== null || product.getDescription().equals(""))
            return Result.error("A descrição do produto deve ser informada.");
        if (product.getPrice() <= 0)
            return Result.error("O preço do produto deve ser informado.");
        return Result.ok();
    }
    
}
