package com.blocklend.lending.protocol.services;

import com.blocklend.lending.protocol.dtos.request.LoginRequest;
import com.blocklend.lending.protocol.dtos.request.OfferRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.AuthenticateUserResponse;
import com.blocklend.lending.protocol.dtos.response.EmailResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;
import com.blocklend.lending.protocol.exceptions.EmailAlreadyExistException;
import com.blocklend.lending.protocol.exceptions.UserNotFoundException;

public interface UserService {

    AuthenticateUserResponse register(RegisterUserRequest registerUserRequest) throws EmailAlreadyExistException;
    OtpVerificationResponse verifyEmail(VerificationRequest request);
   EmailResponse sendOffer(OfferRequest offerRequest);
    EmailResponse rejectOffer(String email);
    EmailResponse serviceLoan(String email);
}
