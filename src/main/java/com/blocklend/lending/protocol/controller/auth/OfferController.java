package com.blocklend.lending.protocol.controller.auth;

import com.blocklend.lending.protocol.dtos.request.OfferRequest;

import com.blocklend.lending.protocol.dtos.response.EmailResponse;

import com.blocklend.lending.protocol.exceptions.EmailAlreadyExistException;
import com.blocklend.lending.protocol.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OfferController {

        private final UserService userService;

        @PostMapping("/createOffer")
        public ResponseEntity<EmailResponse> createOfferMail(@RequestBody OfferRequest request) throws EmailAlreadyExistException {
            return ResponseEntity.status(CREATED).body(userService.sendOffer(request));
        }

        @PostMapping("/rejectOffer")
        public ResponseEntity<EmailResponse>rejectOfferMail(String email) {
            return ResponseEntity.ok(userService.rejectOffer(email));
        }

        @PostMapping("/serviceLoan")
        public ResponseEntity<EmailResponse>serviceLoanMail(String email){
            return ResponseEntity.ok(userService.serviceLoan(email));
        }



}
