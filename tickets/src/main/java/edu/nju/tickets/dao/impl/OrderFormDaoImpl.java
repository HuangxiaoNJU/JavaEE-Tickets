package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.OrderFormDao;
import edu.nju.tickets.entity.OrderForm;
import edu.nju.tickets.entity.ProjectPrice;
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

}
