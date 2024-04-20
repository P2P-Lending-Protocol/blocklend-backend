package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;

public class OtpCodeIsNotValidException extends OfWeb3Exception {
    public OtpCodeIsNotValidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
