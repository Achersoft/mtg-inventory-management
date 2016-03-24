package com.achersoft.exception;

public class EstaffException extends RuntimeException {
    
    public final EstaffError error;
   
    public EstaffException(EstaffError error) {
        this.error = error;
    }
}
