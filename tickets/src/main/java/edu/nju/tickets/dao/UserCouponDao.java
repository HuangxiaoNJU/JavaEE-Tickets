package edu.nju.tickets.dao;

import edu.nju.tickets.entity.UserCoupon;

public interface UserCouponDao extends BaseDao<UserCoupon, Integer> {

    UserCoupon findByUserIdAndCouponId(Integer userId, Integer couponId);

}
