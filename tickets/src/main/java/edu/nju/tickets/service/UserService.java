package edu.nju.tickets.service;

import com.sun.mail.smtp.SMTPAddressFailedException;
import edu.nju.tickets.vo.UserCouponVO;
import edu.nju.tickets.vo.UserInfoVO;
import edu.nju.tickets.vo.UserRegisterVO;
import edu.nju.tickets.vo.UserStatisticsVO;

import java.util.List;

public interface UserService {

    /**
     * 发送验证码
     *
     * @param email         邮箱
     */
    void sendVerificationCode(final String email) throws SMTPAddressFailedException;

    /**
     * 注册
     *
     * @param vo            注册信息
     */
    void register(final UserRegisterVO vo);

    /**
     * 登录
     *
     * @param email         邮箱
     * @param password      登录密码
     * @return              用户信息
     */
    UserInfoVO login(final String email, final String password);

    /**
     * 注销账户
     *
     * @param email         邮箱
     * @return              注销结果
     */
    boolean cancel(final String email);

    /**
     * 修改用户信息（目前只支持修改昵称）
     *
     * @param email         邮箱
     * @param nickname      修改后昵称
     */
    void changeUserInfo(final String email, final String nickname);

    /**
     * 获取用户信息
     *
     * @param email         邮箱
     * @return              用户信息
     */
    UserInfoVO getUserInfo(final String email);

    /**
     * 兑换优惠券
     *
     * @param email         邮箱
     * @param couponId      优惠券id
     * @param number        兑换数量
     */
    void exchangeCoupon(final String email, final Integer couponId, final int number);

    /**
     * 获取用户优惠券信息
     *
     * @param email         邮箱
     * @return              用户优惠券信息
     */
    List<UserCouponVO> getUserCoupons(final String email);

    /**
     * 获取所有用户统计信息
     *
     * @return              用户统计信息
     */
    UserStatisticsVO getUserStatistics();

}
