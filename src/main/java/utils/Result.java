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
    
    public static Result ok() {
        return new Result();
    }
    
    public static Result error(String error) {
        return new Result(error);
    }
    
    public boolean hasError() {
        return errorMessage != null && !errorMessage.equals("");
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
