package com.hengxunda.springcloud.order.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 自定义分片算法
 */
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    private String tableName;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {
        for (String tableName : availableTargetNames) {
            this.tableName = tableName;
            if (tableName.endsWith(Long.valueOf(preciseShardingValue.getValue()) % 3 + "")) {
                return tableName;
            }
        }
        throw new IllegalArgumentException(tableName);
    }
}
