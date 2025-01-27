package com.TheShopApp.database.services;

import com.TheShopApp.database.models.VerificationCodeModel;
import com.TheShopApp.database.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public void saveVerificationCode(String email, String code){
        VerificationCodeModel verificationCodeModel = new VerificationCodeModel();
        verificationCodeModel.setEmail(email);
        verificationCodeModel.setCode(code);
        verificationCodeModel.setExpiry_time(LocalDateTime.now().plus(15, ChronoUnit.MINUTES));
        verificationCodeRepository.save(verificationCodeModel);
    }

    public VerificationCodeModel getVerificationCodeByEmail(String email){
        return verificationCodeRepository.findByEmail(email);
    }
}
