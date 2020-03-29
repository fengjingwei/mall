package com.hengxunda.springcloud.common.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体编号（唯一标识）
     */
    protected Long id;

    /**
     * 是否是新记录（默认：false），调用setBoolNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    protected boolean boolNewRecord = false;

    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert();

    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate();

    public boolean boolNewRecord() {
        if (id == null) {
            boolNewRecord = true;
        }
        return boolNewRecord;
    }
}
