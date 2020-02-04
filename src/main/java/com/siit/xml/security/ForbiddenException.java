package com.siit.xml.security;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() { }

    public ForbiddenException(String message) {
        super(message);
    }
}