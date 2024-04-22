package com.blocklend.lending.protocol.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerificationRequest {

    private String email;
    private String otp;


}
