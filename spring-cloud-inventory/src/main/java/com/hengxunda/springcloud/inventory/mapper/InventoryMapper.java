package com.hengxunda.springcloud.inventory.mapper;

import com.hengxunda.springcloud.common.persistence.CrudDao;
import com.hengxunda.springcloud.inventory.entity.InventoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InventoryMapper extends CrudDao<InventoryDO> {

    /**
     * 库存扣减
     *
     * @param inventory 实体对象
     * @return rows
     */
    @Update("update inventory set total_inventory = #{totalInventory}," +
            " lock_inventory = #{lockInventory} " +
            " where product_id = #{productId} and total_inventory > 0")
    int decrease(InventoryDO inventory);

    /**
     * 根据商品id找到库存信息
     *
     * @param productId 商品id
     * @return Inventory
     */
    @Select("select * from inventory where product_id = #{productId}")
    InventoryDO findByProductId(String productId);
}
