package com.hengxunda.springcloud.order.service;

import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.service.common.service.BaseService;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService extends BaseService<Order> {

    List<Order> listAll();

    /**
     * 订单支付并进行扣除账户余额，进行库存扣减
     *
     * @param number 订单编号
     * @param amount 支付金额
     * @return
     */
    String orderPay(String number, BigDecimal amount);
}
