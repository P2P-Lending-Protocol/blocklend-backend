package com.blocklend.lending.protocol.controller;

import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class KycControllerTest {





    @Autowired
    private MockMvc mockMvc;

    @Test
    public void verifyEmailEndpoint() {
        ObjectMapper mapper = new ObjectMapper();
        String referenceId = "12345";

        try {
            mockMvc.perform(get("/api/v1/auth/create-inquiry")
//                            .content(mapper.writeValueAsString(request))
                            .param("referenceId", referenceId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
