package com.achersoft.exception;

public class InvalidDataException extends EstaffException {

    public InvalidDataException(String error) {
        super(EstaffError.builder()
                    .code(EstaffError.INVALID_REQUEST_DATA)                                               
                    .message(error)                                               
                    .build());
    }
}
