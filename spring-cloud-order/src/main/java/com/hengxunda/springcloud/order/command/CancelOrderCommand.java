package com.hengxunda.springcloud.order.command;

import com.hengxunda.springcloud.common.a.Assert;
import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.persistence.Command;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelOrderCommand implements Command<String> {

    private final OrderMapper orderMapper;

    @Autowired
    public CancelOrderCommand(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Object execute(String orderNo) {
        final Order order = orderMapper.get(orderNo);
        Assert.isNull(order, String.format("订单[%s]不存在", orderNo));
        final OrderEnum.Status status = OrderEnum.Status.acquireByCode(order.getStatus());
        Assert.state(status == OrderEnum.Status.CANCEL, String.format("订单[%s]已取消状态，禁止重复取消", orderNo));
        Assert.state(status == OrderEnum.Status.PAY_SUCCESS, String.format("订单[%s]已支付，禁止取消", orderNo));
        Assert.state(status == OrderEnum.Status.PAY_FAIL, String.format("订单[%s]失败状态，禁止取消", orderNo));
        order.setStatus(OrderEnum.Status.CANCEL.code());
        orderMapper.update(order);
        return null;
    }
}
