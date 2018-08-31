package com.hengxunda.springcloud.order.service;

import com.hengxunda.springcloud.order.entity.Order;

public interface PaymentService {

    /**
     * 订单支付
     *
     * @param order
     */
    void makePayment(Order order);
}
