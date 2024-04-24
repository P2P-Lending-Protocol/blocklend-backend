package com.blocklend.lending.protocol.controller.kycController;

import com.blocklend.lending.protocol.services.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class KycController {

    private final PersonaService personaService;


    @GetMapping("/create-inquiry")
    public ResponseEntity<String> createInquiry(@RequestParam String referenceId) {
        String inquiryUrl = personaService.createInquiryUrl(referenceId);
        return ResponseEntity.ok(inquiryUrl);
    }

    @GetMapping("/verification-complete")
    public ResponseEntity<String> verificationComplete(@RequestParam(required = false) String inquiryId) {
        // Here you can handle post-verification logic, such as updating user status
        return ResponseEntity.ok("Verification complete. Inquiry ID: " + inquiryId);
    }
}
