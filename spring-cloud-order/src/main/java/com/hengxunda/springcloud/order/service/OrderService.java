package com.hengxunda.springcloud.order.service;

import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.vo.OrderVO;
import com.hengxunda.springcloud.service.common.service.BaseService;

import java.util.List;

public interface OrderService extends BaseService<Order> {

    /**
     * 获取订单
     *
     * @param orderNo 订单编号
     * @return {@link Order}
     */
    Order get(String orderNo);

    /**
     * 查询所有订单
     *
     * @param keyword 关键字
     * @return {@link List<OrderVO>}
     */
    List<OrderVO> listAll(String keyword);
}
