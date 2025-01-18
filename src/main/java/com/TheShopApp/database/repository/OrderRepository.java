package com.TheShopApp.database.repository;

import com.TheShopApp.darajaApi.dtos.OrdersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrdersModel, Long> {
    List<OrdersModel> findByCustomer_id(Long customerId);

    Optional<OrdersModel> findById(Long orderId);
}
