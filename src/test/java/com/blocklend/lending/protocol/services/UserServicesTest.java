package com.blocklend.lending.protocol.services;

import com.blocklend.lending.protocol.dtos.request.LoginRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.AuthenticateUserResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServicesTest {

    @Autowired
    private  UserService userService;


    @Test
    public void RegisterUser() {
        RegisterUserRequest registerUserRequest= RegisterUserRequest
                .builder()
                .username("timory")
                .email("zarahgathoni76@gmail.com")
                .password("ebukizy4u@")
                .build();
        AuthenticateUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.getId()).isNotNull();
    }

    @Test
    public void verifyEmail() {
        VerificationRequest request = new VerificationRequest();
        request.setEmail("ebukizy1@gmail.com");
        request.setOtp("009972");
        OtpVerificationResponse  verificationResponse = userService.verifyEmail(request);
        assertThat(verificationResponse).isNotNull();
        assertTrue(verificationResponse.getVerifiedStatus());
    }

    @Test
    public void testUserLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("ebukizy1@gmail.com");
        loginRequest.setPassword("ebukizy4u@");
        AuthenticateUserResponse authResponse =  userService.authenticate(loginRequest);
        assertThat(authResponse).isNotNull();
        assertThat(authResponse.getId()).isNotNull();
        assertThat(authResponse.getToken()).isNotNull();
    }

}
