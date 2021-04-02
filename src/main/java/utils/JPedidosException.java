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
public class JPedidosException extends RuntimeException {
    private Exception innerException;
    
    public JPedidosException(String message) {
        super(message);
    }
    
    public JPedidosException(Exception innerException){
        this.innerException = innerException;
    }
    
    public JPedidosException(String message, Exception innerException){
        this(message);
        this.innerException = innerException;
    }
    
    @Override
    public String getMessage() {
        if (innerException == null) return super.getMessage();
        return super.getMessage() + "\nInnerException: " + innerException.getMessage();
    }
}
