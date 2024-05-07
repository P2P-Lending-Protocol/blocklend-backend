package com.blocklend.lending.protocol.controller;

import com.blocklend.lending.protocol.dtos.request.LoginRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterEndPoint() {
        ObjectMapper mapper = new ObjectMapper();
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("ebukizy1@gmail.com");
        try {
            mockMvc.perform(post("/api/v1/auth")
                            .content(mapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyEmailEndpoint() {
        ObjectMapper mapper = new ObjectMapper();
        VerificationRequest request = new VerificationRequest();
        request.setEmail("ebukizy1@gmail.com");
        request.setOtp("009972");

        try {
            mockMvc.perform(post("/api/v1/auth/verifyMail")
                            .content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginEndPoint(){
        ObjectMapper mapper = new ObjectMapper();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("ebukizy1@gmail.com");
        loginRequest.setPassword("ebukizy1");
        try{
            mockMvc.perform(post("/api/v1/auth/login")
                            .content(mapper.writeValueAsString(loginRequest))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
