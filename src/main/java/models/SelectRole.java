/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import enums.Role;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class SelectRole implements IModel {
    public Role value;
    public String text;
    
    public SelectRole() { }
    
    public SelectRole(
        Role value,
        String text
    ) {
        this.value = value;
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }

    @Override
    public Result validate() {
        return Result.ok();
    }
}
