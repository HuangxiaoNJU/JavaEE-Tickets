package edu.nju.tickets.dao;

import java.io.Serializable;

public interface BaseDao<T, PK extends Serializable> {

    /**
     * 根据主键获取实体
     */
    T get(final PK id);

    /**
     * 更新实体
     */
    void update(final T entity);

    /**
     * 存储实体到数据库
     */
    PK save(final T entity);

    /**
     * 删除实体
     */
    void delete(final T entity);

    /**
     * 根据主键删除实体
     */
    void delete(final PK id);

}
