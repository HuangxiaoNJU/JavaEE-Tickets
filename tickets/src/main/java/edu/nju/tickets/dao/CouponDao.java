package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Coupon;

import java.util.List;

public interface CouponDao extends BaseDao<Coupon, Integer> {
    List<Coupon> findAllOrderByMoney();
}
