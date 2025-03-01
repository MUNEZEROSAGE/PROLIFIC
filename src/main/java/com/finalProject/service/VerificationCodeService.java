package com.finalProject.service;

import com.finalProject.model.Citizen;
import com.finalProject.model.VerificationCode;
import com.finalProject.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final CitizenService citizenService;

    @Autowired
    public VerificationCodeService(VerificationCodeRepository verificationCodeRepository,
                                   CitizenService citizenService) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.citizenService = citizenService;
    }

    public VerificationCode saveVerificationCode(VerificationCode code) {
        return verificationCodeRepository.save(code);
    }

    public VerificationCode validateCode(String code) {
        VerificationCode verificationCode = verificationCodeRepository.findByCode(code);
        if (verificationCode == null) {
            throw new RuntimeException("Invalid code!");
        } else if (verificationCode.isUsed()) {
            throw new RuntimeException("Code already used!");
        } else if (LocalDateTime.now().isAfter(verificationCode.getExpiresAt())) {
            throw new RuntimeException("Code expired!");
        }
        return verificationCode;
    }

    public void redeemCode(String code, Citizen citizen) {
        VerificationCode verificationCode = validateCode(code);
        citizen.addEcoPoints(verificationCode.getTokens()); // Use addEcoPoints method
        citizenService.updateCitizen(citizen.getId(), citizen);
        verificationCode.setUsed(true);
        verificationCodeRepository.save(verificationCode);
    }

    private final Map<String, Boolean> activeCodes = new ConcurrentHashMap<>();

    public String generateCode() {
        String code = String.format("%04d", new Random().nextInt(10000));
        activeCodes.put(code, true);
        return code;
    }

    public boolean isValidCode(String code) {
        return activeCodes.getOrDefault(code, false);
    }

    public void invalidateCode(String code) {
        activeCodes.remove(code);
    }

}