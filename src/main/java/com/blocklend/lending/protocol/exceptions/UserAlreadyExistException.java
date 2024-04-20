package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends OfWeb3Exception {
    public UserAlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

}
