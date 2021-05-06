package com.lullaby.ssjr.common;

public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException() {
        super();
    }
}
