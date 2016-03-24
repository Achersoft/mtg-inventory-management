package com.achersoft.exception;

public class AuthenticationException extends EstaffException {

    public AuthenticationException(int code, String error) {
        super(EstaffError.builder()
                    .code(code)                                               
                    .message(error)                                               
                    .build());
    }
}
