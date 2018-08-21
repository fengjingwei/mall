package com.hengxunda.springcloud.service.common.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    T get(T entity);

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    List<T> findList(T entity);

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 删除数据
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * 获取分页数据
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<T> findPage(T entity, int pageNo, int pageSize);

}
