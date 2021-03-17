/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import utils.Result;
import validators.IValidator;

/**
 *
 * @author Paulo
 */
public interface IModel {
    Result validate();
}
