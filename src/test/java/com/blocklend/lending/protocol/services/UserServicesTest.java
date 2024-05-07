package com.blocklend.lending.protocol.services;

import com.blocklend.lending.protocol.dtos.request.LoginRequest;
import com.blocklend.lending.protocol.dtos.request.OfferRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.AuthenticateUserResponse;
import com.blocklend.lending.protocol.dtos.response.EmailResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
                .email("atokemmy@gmail.com")
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
    public void sendOffer(){
        OfferRequest offerRequest = new OfferRequest();
        LocalDateTime now = LocalDateTime.now();
        offerRequest.setUserEmail("atokemmy@gmail.com");
        offerRequest.setAmount(1000000);
        offerRequest.setInterest(15);
        offerRequest.setReturnDate(now.plusDays(2));
        EmailResponse response = userService.sendOffer(offerRequest);
        assertThat(response.getMessageId()).isNotNull();
        assertThat(response.getCode()).isNotNull();
    }

    @Test
    public void rejectOffer(){
        EmailResponse response = userService.rejectOffer("ebukizy1@gmail.com");
        assertThat(response.getMessageId()).isNotNull();
        assertThat(response.getCode()).isNotNull();
    }
    @Test
    public void testServiceLoan(){
        EmailResponse response = userService.serviceLoan("ebukizy1@gmail.com");
        assertThat(response.getMessageId()).isNotNull();
        assertThat(response.getCode()).isNotNull();
    }



}
