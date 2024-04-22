package com.blocklend.lending.protocol.services;

import com.blocklend.lending.protocol.data.model.Role;
import com.blocklend.lending.protocol.data.model.User;
import com.blocklend.lending.protocol.data.repository.UserRepository;
import com.blocklend.lending.protocol.dtos.request.LoginRequest;
import com.blocklend.lending.protocol.dtos.request.RegisterUserRequest;
import com.blocklend.lending.protocol.dtos.request.VerificationRequest;
import com.blocklend.lending.protocol.dtos.response.AuthenticateUserResponse;
import com.blocklend.lending.protocol.dtos.response.OtpVerificationResponse;
import com.blocklend.lending.protocol.exceptions.EmailAlreadyExistException;
import com.blocklend.lending.protocol.exceptions.UserNotFoundException;
import com.blocklend.lending.protocol.mailservice.OtpServiceImpl;
import com.blocklend.lending.protocol.mailservice.Recipient;
import com.blocklend.lending.protocol.security.service.JwtServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.blocklend.lending.protocol.data.model.Role.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final OtpServiceImpl otpService;
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;
//    private final ModelMapper modelMapper;


    @Override
    public AuthenticateUserResponse register(RegisterUserRequest registerRequest)  {
        validateDuplicateEmail(registerRequest);
        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .role(USER)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        Recipient recipient = new Recipient();
        recipient.setEmail(registerRequest.getEmail());
        recipient.setName(registerRequest.getUsername());
        otpService.generateOtp(recipient);

        var savedUser = userRepository.save(user);
        return AuthenticateUserResponse.builder()
                .id(savedUser.getId())
                .build();
    }

    private void validateDuplicateEmail(RegisterUserRequest registerUserRequest) throws EmailAlreadyExistException {
        Optional<User> isEmailDuplicate =  userRepository.findByEmail(registerUserRequest.getEmail());
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

    @Override
    public AuthenticateUserResponse authenticate(LoginRequest loginRequest) throws UserNotFoundException {
        // Attempt to find the user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        // Check if the user is active (assuming getStatus() returns a boolean indicating the user's status)
        if (!user.getIsVerified()) {
            throw new UserNotFoundException("User not active");
        }
        // Authenticate the user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        // Generate JWT token for the authenticated user
        String jwtToken = jwtServices.generateToken(user);
        // Return the response containing the user's ID and the JWT token
        return AuthenticateUserResponse.builder()
                .id(user.getId())
                .token(jwtToken)
                .build();
    }

    public User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow( ()-> new UserNotFoundException(" user not found"));
    }


}
