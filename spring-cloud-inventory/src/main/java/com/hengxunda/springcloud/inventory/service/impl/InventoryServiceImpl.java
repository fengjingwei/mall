package com.hengxunda.springcloud.inventory.service.impl;

import com.hengxunda.springcloud.common.exception.ServiceException;
import com.hengxunda.springcloud.inventory.dto.InventoryDTO;
import com.hengxunda.springcloud.inventory.entity.InventoryDO;
import com.hengxunda.springcloud.inventory.mapper.InventoryMapper;
import com.hengxunda.springcloud.inventory.service.InventoryService;
import com.hengxunda.springcloud.service.common.service.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl extends AbstractCrudService<InventoryMapper, InventoryDO> implements InventoryService {

    @Override
    @Transactional
    public Boolean decrease(InventoryDTO inventoryDTO) {
        final InventoryDO entity = dao.findByProductId(inventoryDTO.getProductId());
        entity.setTotalInventory(entity.getTotalInventory() - inventoryDTO.getCount());
        entity.setLockInventory(entity.getLockInventory() + inventoryDTO.getCount());
        final int decrease = dao.decrease(entity);
        if (decrease != 1) {
            throw new ServiceException("库存不足");
        }
        return Boolean.TRUE;
    }

    @Override
    public InventoryDO findByProductId(String productId) {
        return dao.findByProductId(productId);
    }
}
