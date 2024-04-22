package com.blocklend.lending.protocol.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class AuthenticateUserResponse {
    private Long id;
    private String token;

}
