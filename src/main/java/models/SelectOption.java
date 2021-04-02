/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import utils.Result;

/**
 *
 * @author Paulo
 */
public class SelectOption implements IModel {
    public int value;
    public String text;
    
    @Override
    public String toString() {
        return text;
    }

    @Override
    public Result validate() {
        return Result.ok();
    }
}
