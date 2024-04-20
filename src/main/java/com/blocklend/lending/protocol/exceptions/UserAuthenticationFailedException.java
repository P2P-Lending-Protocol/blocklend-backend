package com.blocklend.lending.protocol.exceptions;

public class UserAuthenticationFailedException extends RuntimeException {
    public UserAuthenticationFailedException(String message) {
        super(message);
    }
}
