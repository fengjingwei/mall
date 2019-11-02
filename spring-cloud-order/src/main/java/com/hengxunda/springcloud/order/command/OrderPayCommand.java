package com.hengxunda.springcloud.order.command;

import com.hengxunda.springcloud.common.a.Assert;
import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.persistence.Command;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import com.hengxunda.springcloud.order.rabbitmq.producers.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderPayCommand implements Command<String> {

    private final OrderMapper orderMapper;

    private final OrderProducer orderProducer;

    @Autowired
    public OrderPayCommand(OrderMapper orderMapper, OrderProducer orderProducer) {
        this.orderMapper = orderMapper;
        this.orderProducer = orderProducer;
    }

    @Override
    public Object execute(String orderNo) {
        final Order order = orderMapper.get(orderNo);
        Assert.isNull(order, String.format("订单[%s]不存在", orderNo));
        final OrderEnum.Status status = OrderEnum.Status.acquireByCode(order.getStatus());
        Assert.state(status == OrderEnum.Status.CANCEL, String.format("订单[%s]已取消，无法支付", orderNo));
        Assert.state(status == OrderEnum.Status.PAY_SUCCESS, String.format("订单[%s]已支付，禁止重复支付", orderNo));
        Assert.state(status == OrderEnum.Status.PAY_FAIL, String.format("订单[%s]失败状态，禁止支付", orderNo));
        orderProducer.orderPay(order);
        return String.format("订单[%s]正在支付中...", orderNo);
    }
}
