package com.hengxunda.springcloud.common.persistence;

import java.util.List;

public interface CrudDao<T> extends BaseDao {

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    T get(T entity);

    /**
     * 查询数据列表
     *
     * @param entity
     * @return
     */
    List<T> findList(T entity);

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 删除数据
     *
     * @param entity
     * @return
     */
    int delete(T entity);

}