package com.blocklend.lending.protocol.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OtpVerificationResponse {
    private String message;
    private Boolean verifiedStatus;
    private String email;
}
