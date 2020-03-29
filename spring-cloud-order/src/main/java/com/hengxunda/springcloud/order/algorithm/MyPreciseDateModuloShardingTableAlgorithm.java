package com.hengxunda.springcloud.order.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class MyPreciseDateModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Date> {

    private static final ThreadLocal<DateFormatter> FORMATTER = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("yyyy_MM")));

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Date> shardingValue) {
        return tableNames
                .stream()
                .filter(tableName -> tableName.endsWith(FORMATTER.get().getFormat().format(shardingValue.getValue())))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}