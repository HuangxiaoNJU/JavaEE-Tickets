package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.CouponDao;
import edu.nju.tickets.entity.Coupon;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponDaoImpl extends BaseDaoImpl<Coupon, Integer> implements CouponDao {

    @Override
    public List<Coupon> findAllOrderByMoney() {
        return find("from Coupon c order by c.money");
    }
}
