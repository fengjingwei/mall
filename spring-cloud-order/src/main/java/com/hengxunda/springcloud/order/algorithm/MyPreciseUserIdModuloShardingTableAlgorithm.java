package com.hengxunda.springcloud.order.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class MyPreciseUserIdModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<String> {

    private String tableName;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {
        for (String tableName : availableTargetNames) {
            this.tableName = tableName;
            if (tableName.endsWith(Long.parseLong(preciseShardingValue.getValue()) % availableTargetNames.size() + "")) {
                return tableName;
            }
        }
        throw new IllegalArgumentException(tableName);
    }
}
