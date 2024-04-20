package com.blocklend.lending.protocol.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "otp")
public class Otp {

    @Id
    @UuidGenerator
    private String id;

    private String email;

    private String otpCode;

    private LocalDateTime createdAt;

    private LocalDateTime confirmedAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
