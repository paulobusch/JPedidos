/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import context.IAuthContext;
import entities.EntityBase;
import utils.Result;


/**
 *
 * @author otávio
 */
public interface IValidator<TModel> {
    Result validate(TModel model);
}