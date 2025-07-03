package com.company.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message);
    }

}
