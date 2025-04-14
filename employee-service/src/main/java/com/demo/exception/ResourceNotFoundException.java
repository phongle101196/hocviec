package com.demo.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResourceNotFoundException extends RuntimeException {

    private int status;
    private Object data;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, int status) {
        super(message);
        this.status = status;
    }

    public ResourceNotFoundException(String message, int status, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

}
