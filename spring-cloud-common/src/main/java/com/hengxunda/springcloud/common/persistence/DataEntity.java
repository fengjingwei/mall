package com.hengxunda.springcloud.common.persistence;

import com.hengxunda.springcloud.common.utils.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    private Long createBy; // 创建者
    private LocalDateTime createTime; // 创建时间
    private Long updateBy; // 更新者
    private LocalDateTime updateTime; // 更新时间

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() {
        LocalDateTime now = DateUtils.getLocalDateTime();
        this.createTime = now;
        this.updateTime = now;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate() {
        this.updateTime = DateUtils.getLocalDateTime();
    }

}
