package com.TheShopApp.database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mpesa_receipt_number;
    private LocalDateTime trans_date;
    private String phone_number;
    private BigDecimal amount;

    @Column(name = "merchant_request_id")
    private String merchantRequestId;
    private String checkout_request_id;
    private int result_code;
    private String result_desc;

}
