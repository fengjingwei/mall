package com.hengxunda.springcloud.inventory.controller;

import com.hengxunda.springcloud.common.a.Assert;
import com.hengxunda.springcloud.inventory.dto.InventoryDTO;
import com.hengxunda.springcloud.inventory.entity.Inventory;
import com.hengxunda.springcloud.inventory.service.InventoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @ApiOperation(value = "扣减商品库存")
    @ApiImplicitParam(name = "inventoryDTO", value = "请求对象", required = true, paramType = "body", dataType = "InventoryDTO")
    @PostMapping("decrease")
    public String decrease(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.decrease(inventoryDTO);
    }

    @ApiOperation(value = "获取商品总库存")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true, paramType = "query", dataType = "String")
    @GetMapping("findByProductId")
    public Integer findByProductId(@RequestParam("productId") String productId) {
        final Inventory inventory = inventoryService.findByProductId(productId);
        Assert.isNull(inventory, String.format("商品[%s]不存在", productId));
        return inventory.getTotalInventory();
    }
}
