package com.blocklend.lending.protocol.mailservice;

import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.EmailResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;

public interface OtpService {
    EmailResponse generateOtp( Recipient recipient);

    void validateOtpCode(VerificationRequest request);

}
