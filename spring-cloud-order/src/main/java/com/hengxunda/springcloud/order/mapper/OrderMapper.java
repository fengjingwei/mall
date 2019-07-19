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
    @Override
    int insert(Order order);

    /**
     * 更新订单
     *
     * @param order 订单对象
     * @return rows
     */
    @Override
    int update(Order order);

    /**
     * 获取所有的订单
     *
     * @param keyword 关键字
     * @return
     */
    List<Order> listAll(@Param("keyword") String keyword);

    /**
     * 获取某个订单
     *
     * @param orderNo
     * @return
     */
    Order get(@Param("orderNo") String orderNo);
}
