package com.TheShopApp.database.services;

import com.TheShopApp.darajaApi.dtos.OrdersModel;
import com.TheShopApp.darajaApi.dtos.ProductsItem;
import com.TheShopApp.database.models.OrderDTO;
import com.TheShopApp.database.models.OrderedProductDTO;
import com.TheShopApp.database.models.Payments;
import com.TheShopApp.database.repository.OrderRepository;
import com.TheShopApp.database.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }


    public List<OrderDTO> getOrderByCustomerId(Long customerId) {
        List<OrdersModel> orders = orderRepository.findByCustomer_id(customerId);

        return orders.stream().map(ordersModel -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(ordersModel.getId());
            orderDTO.setOrderDate(ordersModel.getOrder_date().toString());
            orderDTO.setMerchant_request_id(ordersModel.getMerchant_request_id());

            Payments payments = paymentRepository.findByMerchantRequestId(ordersModel.getMerchant_request_id());

            orderDTO.setAmountPaid(payments.getAmount());

            orderDTO.setOrderedProducts(ordersModel.getProducts().stream().map(productsItem -> {
                OrderedProductDTO productDTO = new OrderedProductDTO();
                productDTO.setProductId(productsItem.getId());
                productDTO.setQuantity(productsItem.getQuantity());
                productDTO.setProductName(productsItem.getItemsModel().getProduct_name());
                productDTO.setImageUrl(productsItem.getItemsModel().getImage_url());
                return productDTO;
            }).collect(Collectors.toList()));
            return orderDTO;
        }).collect(Collectors.toList());
    }

    public Long saveOrder(OrdersModel ordersModel) {
        if (ordersModel.getProducts() != null){
            for (ProductsItem productsItem : ordersModel.getProducts()) {
                productsItem.setOrders(ordersModel);
            }
        }

        OrdersModel savedOrder = orderRepository.save(ordersModel);

        return savedOrder.getId();

    }
}
