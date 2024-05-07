package com.blocklend.lending.protocol.dtos.request;

import com.blocklend.lending.protocol.data.model.Role;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterUserRequest {
    private String email;

}
