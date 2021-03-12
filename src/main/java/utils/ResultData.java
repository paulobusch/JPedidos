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
public class ResultData<Type> {
    protected String errorMessage;
    
    private Type data;
    
    protected ResultData() { }
    protected ResultData(String error) {
        this.errorMessage = error;
    }
    
    protected ResultData(Type data) {
        data = data;
    }
    
    public static <Type> ResultData<Type> Ok(Type data) {
        return new ResultData<Type>(data);
    }
    
    public static <Type> ResultData<Type> Error(String error) {
        return new ResultData<Type>(error);
    }
    
    public boolean HasError() {
        return errorMessage != null && !errorMessage.equals("");
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public Type getData() {
        return data;
    }
}
