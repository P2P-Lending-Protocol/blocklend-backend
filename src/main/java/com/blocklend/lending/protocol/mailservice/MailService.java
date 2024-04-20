package com.blocklend.lending.protocol.mailservice;


import com.blocklend.lending.protocol.dtos.request.EmailRequest;
import com.blocklend.lending.protocol.dtos.response.EmailResponse;

public interface MailService {


    EmailResponse sendMail(EmailRequest emailRequest);
}