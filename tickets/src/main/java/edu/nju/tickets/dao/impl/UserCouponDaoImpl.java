package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.UserCouponDao;
import edu.nju.tickets.entity.UserCoupon;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCouponDaoImpl extends BaseDaoImpl<UserCoupon, Integer> implements UserCouponDao {

    @Override
    public UserCoupon findByUserIdAndCouponId(Integer userId, Integer couponId) {
        List<UserCoupon> userCouponList =  find("from UserCoupon uc where uc.userId=? and uc.couponId=?", userId, couponId);
        return userCouponList.isEmpty() ? null : userCouponList.get(0);
    }

}
