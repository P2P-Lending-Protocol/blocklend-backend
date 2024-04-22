package com.blocklend.lending.protocol.services;

import com.blocklend.lending.protocol.dtos.request.LoginRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.AuthenticateUserResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;
import com.blocklend.lending.protocol.exceptions.EmailAlreadyExistException;
import com.blocklend.lending.protocol.exceptions.UserNotFoundException;

public interface UserService {

    AuthenticateUserResponse register(RegisterUserRequest registerUserRequest) throws EmailAlreadyExistException;
    AuthenticateUserResponse authenticate(LoginRequest loginRequest) throws UserNotFoundException;
    OtpVerificationResponse verifyEmail(VerificationRequest request);

}
