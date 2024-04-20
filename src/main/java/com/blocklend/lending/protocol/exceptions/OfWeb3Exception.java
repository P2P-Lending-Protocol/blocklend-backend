package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;

public class OfWeb3Exception extends RuntimeException{
    private HttpStatus httpStatus;

    public OfWeb3Exception(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public OfWeb3Exception(Throwable throwable) {
        super(throwable);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
