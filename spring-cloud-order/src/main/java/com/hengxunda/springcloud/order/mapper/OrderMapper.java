package com.hengxunda.springcloud.order.mapper;

import com.hengxunda.springcloud.common.persistence.CrudDao;
import com.hengxunda.springcloud.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends CrudDao<Order> {

    /**
     * 保存订单
     *
     * @param order 订单对象
     * @return rows
     */
    int insert(Order order);

    /**
     * 更新订单
     *
     * @param order 订单对象
     * @return rows
     */
    int update(Order order);

    /**
     * 获取所有的订单
     *
     * @return List<Order>
     */
    List<Order> listAll();

    /**
     * 获取某个订单
     *
     * @param number
     * @return
     */
    Order get(@Param("number") String number);
}
