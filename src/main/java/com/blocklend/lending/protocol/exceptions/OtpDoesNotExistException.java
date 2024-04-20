package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;

public class OtpDoesNotExistException extends OfWeb3Exception {

    public OtpDoesNotExistException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
