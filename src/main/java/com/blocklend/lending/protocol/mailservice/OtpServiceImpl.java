package com.blocklend.lending.protocol.mailservice;

import com.blocklend.lending.protocol.data.model.Otp;
import com.blocklend.lending.protocol.data.repository.OtpRepository;
import com.blocklend.lending.protocol.dtos.request.EmailRequest;
import com.blocklend.lending.protocol.dtos.response.EmailResponse;
import com.blocklend.lending.protocol.exceptions.OtpCodeIsNotValidException;
import com.blocklend.lending.protocol.exceptions.OtpDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements  OtpService{

    private final OtpRepository otpRepository;
    private final MailService mailService;
    private final TemplateEngine templateEngine;


    @Override
    public EmailResponse generateOtp(Recipient recipient) {
         String sixDigitOtpCode =  generateSixDigitOtpCode();
         Otp otp = new Otp();
         otp.setEmail(recipient.getEmail());
         otp.setOtpCode(sixDigitOtpCode);
        EmailResponse emailResponse =  emailOtpCode(recipient.getEmail(),  otp.getOtpCode(), recipient.getName());
            otpRepository.save(otp);
        return emailResponse;
    }


    private EmailResponse emailOtpCode(String userEmail, String otpCode, String fullName) {
        //this sends a verification email to user using thymeleaf template
        Recipient recipient = new Recipient();
        recipient.setEmail(userEmail);
        recipient.setName(fullName);
        final Context context = new Context();
        context.setVariables(	//these are the placeholders in the email template
                Map.of(
                        "fullName", fullName,
                        "OTP_Code", otpCode
                )
        );
        final String content = templateEngine.process("verify_mail", context);
        List<Recipient> recipients = List.of(
                recipient
        );
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipients(recipients);
        emailRequest.setSubject("Email verification");
        emailRequest.setHtmlContent(content);
       return  mailService.sendMail(emailRequest);
    }

    @Override
    public String validateOtpCode(String email, String givenOtpCode) {
        Otp foundOtp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new OtpDoesNotExistException("This otp does not exist"));
        String otpCode = foundOtp.getOtpCode();
        if(!Objects.equals(otpCode, givenOtpCode))
            throw new OtpCodeIsNotValidException(String.format("The otp code %s is not valid", givenOtpCode));
        return "successfully validated";
    }

    private String generateSixDigitOtpCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) builder.append(secureRandom.nextInt(10));
        return builder.toString();
    }

}
