package com.hengxunda.springcloud.inventory.controller;

import com.hengxunda.springcloud.inventory.dto.InventoryDTO;
import com.hengxunda.springcloud.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping("decrease")
    public Boolean decrease(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.decrease(inventoryDTO);
    }

    @RequestMapping("findByProductId")
    public Integer findByProductId(@RequestParam("productId") String productId) {
        log.info("productId = {}", productId);
        return inventoryService.findByProductId(productId).getTotalInventory();
    }
}
