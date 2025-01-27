package com.TheShopApp.database.repository;

import com.TheShopApp.database.models.VerificationCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeModel, Long> {
    VerificationCodeModel findByEmail(String email);
}
