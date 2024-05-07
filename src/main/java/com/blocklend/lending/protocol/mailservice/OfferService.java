package com.blocklend.lending.protocol.mailservice;


import com.blocklend.lending.protocol.data.model.Otp;
import com.blocklend.lending.protocol.dtos.request.EmailRequest;
import com.blocklend.lending.protocol.dtos.request.OfferRequest;
import com.blocklend.lending.protocol.dtos.response.EmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final MailService mailService;
    private final TemplateEngine templateEngine;



public EmailResponse sendOfferEmail(OfferRequest offerRequest) {
    //this sends a verification email to user using thymeleaf template
    String userName = getExtractUserName(offerRequest.getUserEmail());
    Recipient recipient = new Recipient();
    recipient.setEmail(offerRequest.getUserEmail());
    recipient.setName(userName);
    final Context context = new Context();
    assert userName != null;
    context.setVariables(	//these are the placeholders in the email template
            Map.of(
                    "fullName", userName,
                    "amount", offerRequest.getAmount(),
                    "interest", offerRequest.getInterest(),
                    "returnDate", offerRequest.getReturnDate()
            )
    );
    final String content = templateEngine.process("offer_mail", context);
    List<Recipient> recipients = List.of(
            recipient
    );
    EmailRequest emailRequest = new EmailRequest();
    emailRequest.setRecipients(recipients);
    emailRequest.setSubject("Loan Offer Notification");
    emailRequest.setHtmlContent(content);
    return  mailService.sendMail(emailRequest);
}

public EmailResponse rejectOffer(String  email){
    String userName = getExtractUserName(email);
    Recipient recipient = new Recipient();
    recipient.setEmail(email);
    recipient.setName(userName);
    final Context context = new Context();
    assert userName != null;
    context.setVariables(	//these are the placeholders in the email template
            Map.of(
                    "fullName", userName
            )
    );
    final String content = templateEngine.process("reject_mail", context);
    List<Recipient> recipients = List.of(
            recipient
    );
    EmailRequest emailRequest = new EmailRequest();
    emailRequest.setRecipients(recipients);
    emailRequest.setSubject("Loan Offer Rejected");
    emailRequest.setHtmlContent(content);
    return  mailService.sendMail(emailRequest);
}

    private String getExtractUserName(String email) {
        return extractUserName(email);
    }


    public EmailResponse serviceLoanRequest(String email){
    String userName = getExtractUserName(email);
    Recipient recipient = new Recipient();
    recipient.setEmail(email);
    recipient.setName(userName);
    final Context context = new Context();
    assert userName != null;
    context.setVariables(	//these are the placeholders in the email template
            Map.of(
                    "fullName", userName
            )
    );
    final String content = templateEngine.process("service_loan", context);
    List<Recipient> recipients = List.of(
            recipient
    );
    EmailRequest emailRequest = new EmailRequest();
    emailRequest.setRecipients(recipients);
    emailRequest.setSubject("Loan Service");
    emailRequest.setHtmlContent(content);
    return  mailService.sendMail(emailRequest);
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
