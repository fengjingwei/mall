package com.hengxunda.springcloud.order.algorithm;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class MyRangeDateModuloShardingTableAlgorithm implements RangeShardingAlgorithm<Date> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyRangeDateModuloShardingTableAlgorithm.class);

    private static final ThreadLocal<DateFormatter> FORMATTER = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("yyyy_MM")));

    @Override
    public Collection<String> doSharding(Collection<String> tableNames, RangeShardingValue<Date> shardingValue) {
        final Range<Date> valueRange = shardingValue.getValueRange();

        final Calendar startTime = Calendar.getInstance();
        startTime.setTime(valueRange.lowerEndpoint());
        startTime.set(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), 1);

        final Calendar endTime = Calendar.getInstance();
        endTime.setTime(valueRange.upperEndpoint());
        endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH), 2);

        final Collection<String> targetTableNames = Sets.newLinkedHashSet();
        while (startTime.before(endTime)) {
            final String tableName = String.format("%s_%s", shardingValue.getLogicTableName(), FORMATTER.get().getFormat().format((startTime.getTime())));
            if (tableNames.contains(tableName)) {
                targetTableNames.add(tableName);
            }
            startTime.add(Calendar.MONTH, 1);
        }
        LOGGER.info("targetTableNames -> {}", targetTableNames);
        return targetTableNames;
    }
}