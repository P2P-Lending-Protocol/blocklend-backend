package com.blocklend.lending.protocol.mailservice;

import com.blocklend.lending.protocol.data.model.Otp;
import com.blocklend.lending.protocol.data.repository.OtpRepository;
import com.blocklend.lending.protocol.dtos.request.EmailRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
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

import static java.time.LocalDateTime.now;

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
        EmailResponse emailResponse =  emailOtpCode(recipient.getEmail(),  otp.getOtpCode());
            otpRepository.save(otp);
        return emailResponse;
    }


    private EmailResponse emailOtpCode(String userEmail, String otpCode) {
        //this sends a verification email to user using thymeleaf template
        String userName = extractUserName(userEmail);
        Recipient recipient = new Recipient();
        recipient.setEmail(userEmail);
        recipient.setName(userName);
        final Context context = new Context();
        assert userName != null;
        context.setVariables(	//these are the placeholders in the email template
                Map.of(
                        "fullName", userName,
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
//    @Transactional
    public void validateOtpCode(VerificationRequest request) {
        Otp foundOtp = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new OtpDoesNotExistException("This otp does not exist"));
        String otpCode = foundOtp.getOtpCode();
        if(!Objects.equals(otpCode, request.getOtp()))
            throw new OtpCodeIsNotValidException(String.format("The otp code %s is not valid", request.getOtp()));
        foundOtp.setConfirmedAt(now());
        otpRepository.save(foundOtp);
    }

    private String generateSixDigitOtpCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) builder.append(secureRandom.nextInt(10));
        return builder.toString();
    }

    private String extractUserName(String email) {
        // Split the email at '@' and take the first part (username)
        String[] parts = email.split("@");
        if (parts.length > 1) {
            return parts[0]; // Return the username
        } else {
            return null; // Return null or handle the error as needed
        }
    }

}
