package com.hengxunda.springcloud.inventory.service.impl;

import com.hengxunda.springcloud.common.a.Assert;
import com.hengxunda.springcloud.inventory.dto.InventoryDTO;
import com.hengxunda.springcloud.inventory.entity.Inventory;
import com.hengxunda.springcloud.inventory.mapper.InventoryMapper;
import com.hengxunda.springcloud.inventory.service.InventoryService;
import com.hengxunda.springcloud.service.common.service.AbstractCrudService;
import lombok.extern.log4j.Log4j2;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class InventoryServiceImpl extends AbstractCrudService<InventoryMapper, Inventory> implements InventoryService {

    @Override
    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public String decrease(InventoryDTO inventoryDTO) {
        log.info("{}", "===========执行try扣减库存方法==========");
        final Inventory entity = dao.findByProductId(inventoryDTO.getProductId());
        entity.setTotalInventory(entity.getTotalInventory() - inventoryDTO.getCount());
        entity.setLockInventory(entity.getLockInventory() + inventoryDTO.getCount());
        final int rows = dao.decrease(entity);
        Assert.state(rows != 1, "库存不足");
        return "true";
    }

    public String confirmMethod(InventoryDTO inventoryDTO) {
        log.info("{}", "===========执行confirm扣减库存方法==========");
        dao.confirm(inventoryDTO);
        return "true";
    }

    public String cancelMethod(InventoryDTO inventoryDTO) {
        log.info("{}", "===========执行cancel扣减库存方法==========");
        final int rows = dao.cancel(inventoryDTO);
        Assert.state(rows != 1, "取消扣减库存异常");
        return "true";
    }

    @Override
    public Inventory findByProductId(String productId) {
        return dao.findByProductId(productId);
    }
}
