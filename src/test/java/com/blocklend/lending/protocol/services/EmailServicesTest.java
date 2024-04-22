package com.blocklend.lending.protocol.services;

import com.blocklend.lending.protocol.dtos.response.EmailResponse;
import com.blocklend.lending.protocol.mailservice.OtpService;
import com.blocklend.lending.protocol.mailservice.Recipient;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmailServicesTest {

    @Autowired
    private  OtpService optServices;


    @Test
    public void testSendOpt() {
        Recipient recipient = new Recipient();
        recipient.setName("kemmy");
        recipient.setEmail("atokemmy@gmail.com");
        EmailResponse response = optServices.generateOtp(recipient);
        assertThat(response.getMessageId()).isNotNull();
        assertThat(response.getCode()).isNotNull();
        assertEquals(201, response.getCode());
    }
    @Test
    public void testVerifyOtp() {
//        String optCode = optServices.validateOtpCode("ebukizy1@gmail.com", "186747");
//        assertThat(optCode).isNotNull();
//        assertEquals(optCode, "successfully validated");

    }





}