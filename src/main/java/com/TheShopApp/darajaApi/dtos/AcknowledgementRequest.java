package com.TheShopApp.darajaApi.dtos;

import lombok.Data;

@Data
public class AcknowledgementRequest {
    private STKPushAsynchronousResponse stkPushAsynchronousResponse;
    private OrdersModel ordersModel;
}
