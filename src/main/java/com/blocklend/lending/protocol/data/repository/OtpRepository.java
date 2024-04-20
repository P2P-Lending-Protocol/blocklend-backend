package com.blocklend.lending.protocol.data.repository;


import com.blocklend.lending.protocol.data.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository <Otp,String>{

    List<Otp> findAllByEmail(String userEmail);

    Optional<Otp> findByEmail(String email);
}

