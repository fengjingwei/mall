package com.hengxunda.springcloud.inventory.mapper;

import com.hengxunda.springcloud.common.persistence.CrudDao;
import com.hengxunda.springcloud.inventory.dto.InventoryDTO;
import com.hengxunda.springcloud.inventory.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InventoryMapper extends CrudDao<Inventory> {

    /**
     * 库存扣减
     *
     * @param inventory
     * @return
     */
    @Update("update t_inventory set total_inventory = #{totalInventory}, " +
            "lock_inventory = #{lockInventory} " +
            "where product_id = #{productId} and total_inventory > 0")
    int decrease(Inventory inventory);

    /**
     * (确认)库存扣减
     *
     * @param inventoryDTO
     * @return
     */
    @Update("update t_inventory set " +
            "lock_inventory = lock_inventory - #{count} " +
            "where product_id = #{productId} and lock_inventory > 0")
    int confirm(InventoryDTO inventoryDTO);

    /**
     * (取消)库存扣减
     *
     * @param inventoryDTO
     * @return
     */
    @Update("update t_inventory set total_inventory = total_inventory + #{count}, " +
            "lock_inventory = lock_inventory - #{count} " +
            "where product_id = #{productId} and lock_inventory > 0")
    int cancel(InventoryDTO inventoryDTO);

    /**
     * 根据商品id找到库存信息
     *
     * @param productId
     * @return
     */
    @Select("select * from t_inventory where product_id = #{productId}")
    Inventory findByProductId(String productId);
}
