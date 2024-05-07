package com.blocklend.lending.protocol.services;

import com.blocklend.lending.protocol.data.model.User;
import com.blocklend.lending.protocol.data.repository.UserRepository;

import com.blocklend.lending.protocol.dtos.request.OfferRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.AuthenticateUserResponse;
import com.blocklend.lending.protocol.dtos.response.EmailResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;
import com.blocklend.lending.protocol.exceptions.EmailAlreadyExistException;
import com.blocklend.lending.protocol.exceptions.UserNotFoundException;
import com.blocklend.lending.protocol.mailservice.OfferService;
import com.blocklend.lending.protocol.mailservice.OtpServiceImpl;
import com.blocklend.lending.protocol.mailservice.Recipient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.blocklend.lending.protocol.data.model.Role.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private  final OtpServiceImpl otpService;
    private final OfferService offerService;


    @Override
    public AuthenticateUserResponse register(RegisterUserRequest registerRequest)  {
        validateDuplicateEmail(registerRequest.getEmail());
        String username = extractUserName(registerRequest.getEmail());
        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(username)
                .build();

        Recipient recipient = new Recipient();
        recipient.setEmail(registerRequest.getEmail());
        recipient.setName(username);
        otpService.generateOtp(recipient);

        var savedUser = userRepository.save(user);
        return AuthenticateUserResponse.builder()
                .id(savedUser.getId())
                .build();
    }

    private void validateDuplicateEmail(String email) {
        Optional<User> isEmailDuplicate =  userRepository.findByEmail(email);
        if (isEmailDuplicate.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists");
        }
    }

//    @Transactional
    public OtpVerificationResponse verifyEmail(VerificationRequest request) {
        User foundUser = userRepository.findByEmail(request.getEmail())
          .orElseThrow(() -> new UserNotFoundException("User not found"));
        request.setEmail(foundUser.getEmail());
        request.setOtp(request.getOtp());
         otpService.validateOtpCode(request);
         foundUser.setIsVerified(Boolean.TRUE);
         userRepository.save(foundUser);

        return OtpVerificationResponse.builder()
                        .message("successfully verified")
                        .verifiedStatus(foundUser.getIsVerified())
                        .email(foundUser.getEmail())
                        .build();
    }

    public EmailResponse sendOffer(OfferRequest offerRequest) {
      findUserByUsername(offerRequest.getUserEmail());
        return  offerService.sendOfferEmail(offerRequest);
    }


    public EmailResponse rejectOffer(String email){
        findUserByUsername(email);
        return  offerService.rejectOffer(email);
    }

    @Override
    public EmailResponse serviceLoan(String email) {
        findUserByUsername(email);
        return offerService.serviceLoanRequest(email);
    }


    public User findUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow( ()-> new UserNotFoundException(" user not found"));
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
