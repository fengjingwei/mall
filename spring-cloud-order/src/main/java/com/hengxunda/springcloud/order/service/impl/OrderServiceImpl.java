package com.hengxunda.springcloud.order.service.impl;

import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.utils.SnowFlakeUtils;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import com.hengxunda.springcloud.order.service.OrderService;
import com.hengxunda.springcloud.order.service.PaymentService;
import com.hengxunda.springcloud.service.common.redis.RedisHelper;
import com.hengxunda.springcloud.service.common.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl extends AbstractCrudService<OrderMapper, Order> implements OrderService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RedisHelper redisHelper;

    private static String key(Order order) {

        return "order_" + order.getNumber();
    }

    @Override
    public List<Order> listAll() {

        return dao.listAll();
    }

    @Override
    @Transactional
    public String orderPay(String number, BigDecimal amount) {

        Order order = Order.builder().number(number).totalAmount(amount).status(OrderEnum.Status.PAYING.code()).build();
        final int rows = dao.update(order);

        if (rows > 0) {
            paymentService.makePayment(dao.get(number));
        }

        return "success";
    }

    @Override
    @Transactional
    public Order save(Order order) {
        if (order.boolNewRecord()) {
            order.setNumber(SnowFlakeUtils.getInstance().getId());
            order.setStatus(OrderEnum.Status.NOT_PAY.code());
            order.preInsert();
            dao.insert(order);

            redisHelper.putObject(key(order), order, 0b1111L);
        }
        return order;
    }
}
