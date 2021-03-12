/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Paulo
 */
public class Result {
    protected String errorMessage;
    
    protected Result() { }
    protected Result(String error) {
        this.errorMessage = error;
    }
    
    public static Result Ok() {
        return new Result();
    }
    
    public static Result Error(String error) {
        return new Result(error);
    }
    
    public boolean HasError() {
        return errorMessage != null && !errorMessage.equals("");
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
