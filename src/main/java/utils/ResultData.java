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
        this.data = data;
    }
    
    public static <Type> ResultData<Type> ok(Type data) {
        return new ResultData<Type>(data);
    }
    
    public static <Type> ResultData<Type> error(String error) {
        return new ResultData<Type>(error);
    }
    
    public boolean hasError() {
        return errorMessage != null && !errorMessage.equals("");
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public Type getData() {
        return data;
    }
}
