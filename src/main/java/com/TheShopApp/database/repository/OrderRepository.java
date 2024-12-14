package com.TheShopApp.database.repository;

import com.TheShopApp.darajaApi.dtos.OrdersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersModel, Long> {
    List<OrdersModel> findByCustomer_id(Long customerId);
}
