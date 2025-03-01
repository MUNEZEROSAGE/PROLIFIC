package com.finalProject.repository;

import com.finalProject.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    // Find a verification code by its code value
    VerificationCode findByCode(String code);
}