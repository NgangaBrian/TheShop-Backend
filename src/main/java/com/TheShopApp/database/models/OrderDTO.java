package com.TheShopApp.database.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private String orderDate;
    private String merchant_request_id;
    private BigDecimal amountPaid;
    private List<OrderedProductDTO> orderedProducts;
}
