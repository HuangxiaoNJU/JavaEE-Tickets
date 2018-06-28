package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.BaseDao;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

    @Resource
    protected HibernateTemplate hibernateTemplate;

    private Class<T> entityClass;

    protected BaseDaoImpl() {
        Class clazz = getClass();
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) types[0];
        }
    }

    @Override
    public List<T> findAll() {
        return hibernateTemplate.loadAll(entityClass);
    }

    @Override
    public T get(PK id) {
        return hibernateTemplate.get(entityClass, id);
    }

    @Override
    public void update(T entity) {
        hibernateTemplate.update(entity);
    }

    @Override
    public PK save(T entity) {
        return (PK) hibernateTemplate.save(entity);
    }

    @Override
    public void delete(T entity) {
        hibernateTemplate.delete(entity);
    }

    @Override
    public void delete(PK id) {
        T entity = get(id);
        if (entity != null) {
            delete(entity);
        }
    }

    protected List<T> find(String query, Object... params) {
        return (List<T>) hibernateTemplate.find(query, params);
    }

}
