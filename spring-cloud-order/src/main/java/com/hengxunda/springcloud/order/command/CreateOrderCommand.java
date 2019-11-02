package com.hengxunda.springcloud.order.command;

import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.persistence.Command;
import com.hengxunda.springcloud.common.utils.SnowFlakeUtils;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import com.hengxunda.springcloud.service.common.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CreateOrderCommand implements Command<Order> {

    private final OrderMapper orderMapper;

    private final RedisHelper redisHelper;

    @Autowired
    public CreateOrderCommand(OrderMapper orderMapper, RedisHelper redisHelper) {
        this.orderMapper = orderMapper;
        this.redisHelper = redisHelper;
    }

    @Override
    public Object execute(Order order) {
        if (order.boolNewRecord()) {
            order.setOrderNo(SnowFlakeUtils.getId());
            order.setStatus(OrderEnum.Status.NOT_PAY.code());
            order.preInsert();
            orderMapper.insert(order);

            redisHelper.putObject("order_" + order.getOrderNo(), order, 0b1111L, TimeUnit.MINUTES);
        }
        return null;
    }
}
