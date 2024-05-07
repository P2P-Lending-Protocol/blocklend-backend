package com.blocklend.lending.protocol.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OfferRequest {
    private String userEmail;
    private long amount;
    private long interest;
    private LocalDateTime returnDate;
}
