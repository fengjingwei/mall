package com.hengxunda.springcloud.common.persistence;

import com.hengxunda.springcloud.common.utils.DateUtils;
import lombok.Data;

import java.util.Date;

@Data
public abstract class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    private Long createBy; // 创建者
    private Date createTime; // 创建时间
    private Long updateBy; // 更新者
    private Date updateTime; // 更新时间

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() {
        this.createTime = DateUtils.now();
        this.updateTime = this.createTime;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    @Override
    public void preUpdate() {
        this.updateTime = DateUtils.now();
    }

}
