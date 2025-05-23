package com.TheShopApp.database.repository;

import com.TheShopApp.database.models.Payments;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payments, Long> {
    public Payments findByMerchantRequestId(String merchant_request_id);
}
