package com.hengxunda.springcloud.order.algorithm;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

public class MyRangeShardingAlgorithm implements RangeShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> rangeShardingValue) {
        final Range<String> valueRange = rangeShardingValue.getValueRange();
        final Long lowerEndpoint = Long.valueOf(valueRange.lowerEndpoint());
        final Long upperEndpoint = Long.valueOf(valueRange.upperEndpoint());
        final ArrayList<String> collection = Lists.newArrayList();
        for (Long i = lowerEndpoint; i <= upperEndpoint; i++) {
            for (String tableName : availableTargetNames) {
                if (tableName.endsWith(i % availableTargetNames.size() + "")) {
                    collection.add(tableName);
                }
            }
        }
        return collection;
    }
}
