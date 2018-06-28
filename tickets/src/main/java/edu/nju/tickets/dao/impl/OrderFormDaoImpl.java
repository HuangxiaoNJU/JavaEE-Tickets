package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.OrderFormDao;
import edu.nju.tickets.entity.OrderForm;
import edu.nju.tickets.entity.ProjectPrice;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderFormDaoImpl extends BaseDaoImpl<OrderForm, Integer> implements OrderFormDao {

    @Override
    public List<OrderForm> findByUserIdOrderByCreateTimeDesc(Integer userId) {
        return find("from OrderForm o where o.userId=? order by o.createTime desc", userId);
    }

    @Override
    public List<OrderForm> findByUserIdAndStateOrderByCreateTimeDesc(Integer userId, int state) {
        return find("from OrderForm o where o.userId=? and o.state=? order by o.createTime desc", userId, state);
    }

    @Override
    public List<OrderForm> findByState(int state) {
        return find("from OrderForm o where o.state=?", state);
    }

    @Override
    public List<OrderForm> findByProjectPrice(ProjectPrice projectPrice) {
        return find("from OrderForm o where o.projectPrice=?", projectPrice);
    }

    @Override
    public Double sumTotalPriceByUserId(Integer userId) {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select sum(o.totalPrice) from OrderForm o where o.userId=?1");

        query.setParameter(1, userId);
        return query.uniqueResult() == null ? 0d : (Double) query.uniqueResult();
    }

    @Override
    public Double sumTotalPriceByUserIdAndState(Integer userId, int state) {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select sum(o.totalPrice) from OrderForm o where o.userId=?1 and o.state=?2");

        query.setParameter(1, userId);
        query.setParameter(2, state);

        return query.uniqueResult() == null ? 0d : (Double) query.uniqueResult();
    }

    @Override
    public Long countByUserIdAndState(Integer userId, int state) {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select count(o.id) from OrderForm o where o.userId=?1 and o.state=?2");

        query.setParameter(1, userId);
        query.setParameter(2, state);
        return (Long) query.uniqueResult();
    }

    @Override
    public  List<OrderForm> findByProjectPriceId(Integer projectPriceId) {
        return find("from OrderForm o where o.project_price_id=?", projectPriceId);
    }

}
