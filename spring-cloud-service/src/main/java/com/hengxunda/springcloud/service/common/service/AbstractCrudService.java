package com.hengxunda.springcloud.service.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hengxunda.springcloud.common.persistence.BaseEntity;
import com.hengxunda.springcloud.common.persistence.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public abstract class AbstractCrudService<D extends CrudDao<T>, T extends BaseEntity> implements BaseService<T> {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     * @return
     */
    @Transactional
    @Override
    public T save(T entity) {
        if (entity.boolNewRecord()) {
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
        return entity;
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    @Override
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    @Override
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional
    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    /**
     * 分页查询数据
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<T> findPage(T entity, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<T> list = dao.findList(entity);
        return new PageInfo<>(list);
    }

}
