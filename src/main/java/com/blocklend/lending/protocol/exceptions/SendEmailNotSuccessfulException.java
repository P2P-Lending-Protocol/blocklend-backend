package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;

public class SendEmailNotSuccessfulException extends OfWeb3Exception {
    public SendEmailNotSuccessfulException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
