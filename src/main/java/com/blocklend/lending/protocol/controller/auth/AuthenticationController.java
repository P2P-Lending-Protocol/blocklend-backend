package com.blocklend.lending.protocol.controller.auth;

import com.blocklend.lending.protocol.dtos.request.LoginRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.AuthenticateUserResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;
import com.blocklend.lending.protocol.exceptions.EmailAlreadyExistException;
import com.blocklend.lending.protocol.exceptions.UserNotFoundException;
import com.blocklend.lending.protocol.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.CONTINUE;
import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<AuthenticateUserResponse> register(@RequestBody RegisterUserRequest request) throws EmailAlreadyExistException {
        return ResponseEntity.status(CREATED).body(userService.register(request));
    }

    @PostMapping("/verifyMail")
    public ResponseEntity<OtpVerificationResponse> verifyOtp(@RequestBody VerificationRequest request) {
    return ResponseEntity.ok(userService.verifyEmail(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticateUserResponse> login(@RequestBody LoginRequest request) throws UserNotFoundException {
        AuthenticateUserResponse response = userService.authenticate(request);
        return ResponseEntity.ok(response);
    }





}
