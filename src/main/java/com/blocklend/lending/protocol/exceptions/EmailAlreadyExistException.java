package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistException extends OfWeb3Exception{
    public EmailAlreadyExistException(String message) {

        super(message, HttpStatus.BAD_REQUEST);
    }
}
