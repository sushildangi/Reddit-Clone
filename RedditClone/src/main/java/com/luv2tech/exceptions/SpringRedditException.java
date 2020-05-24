package com.luv2tech.exceptions;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}