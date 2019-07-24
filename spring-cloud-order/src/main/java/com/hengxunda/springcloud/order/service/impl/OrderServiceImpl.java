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

import java.util.List;

@Service
public class OrderServiceImpl extends AbstractCrudService<OrderMapper, Order> implements OrderService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RedisHelper redisHelper;

    private static String key(Order order) {
        return "order_" + order.getOrderNo();
    }

    @Override
    public Order get(String orderNo) {
        return dao.get(orderNo);
    }

    @Override
    public List<Order> listAll(String keyword) {
        // 强制路由主库
        // HintManager.getInstance().setMasterRouteOnly();
        return dao.listAll(keyword);
    }

    @Override
    public String orderPay(String orderNo) {
        paymentService.makePayment(dao.get(orderNo));
        return "success";
    }

    @Override
    @Transactional
    public Order save(Order order) {
        if (order.boolNewRecord()) {
            order.setOrderNo(SnowFlakeUtils.getInstance().getId());
            order.setStatus(OrderEnum.Status.NOT_PAY.code());
            order.preInsert();
            dao.insert(order);

            redisHelper.putObject(key(order), order, 0b1111L);
        }
        return order;
    }
}
