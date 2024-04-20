package com.blocklend.lending.protocol.mailservice;

import com.blocklend.lending.protocol.dtos.response.EmailResponse;

public interface OtpService {
    EmailResponse generateOtp( Recipient recipient);

    String validateOtpCode(String email, String otpCode);

}
