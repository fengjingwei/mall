package com.hengxunda.springcloud.order.service.impl;

import com.hengxunda.springcloud.common.utils.Collections3Utils;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import com.hengxunda.springcloud.order.service.OrderService;
import com.hengxunda.springcloud.order.vo.OrderVO;
import com.hengxunda.springcloud.service.common.service.AbstractCrudService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends AbstractCrudService<OrderMapper, Order> implements OrderService {

    @Override
    public Order get(String orderNo) {
        return dao.get(orderNo);
    }

    @Override
    public List<OrderVO> listAll(String keyword) {
        // 强制路由主库
        HintManager.getInstance().setMasterRouteOnly();
        return Collections3Utils.copy(dao.listAll(keyword), OrderVO.class);
    }
}
