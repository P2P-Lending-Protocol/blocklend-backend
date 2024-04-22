package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends OfWeb3Exception{
    public UserNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
