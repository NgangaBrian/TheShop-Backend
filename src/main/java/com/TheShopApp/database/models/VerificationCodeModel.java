package com.TheShopApp.database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "verification_codes")
public class VerificationCodeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String code;
    private LocalDateTime expiry_time;

    @Column(name = "created_at", insertable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updated_at;

}
