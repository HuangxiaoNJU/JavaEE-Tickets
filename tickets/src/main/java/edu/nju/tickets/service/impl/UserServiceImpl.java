package edu.nju.tickets.service.impl;

import com.sun.mail.smtp.SMTPAddressFailedException;
import edu.nju.tickets.dao.*;
import edu.nju.tickets.entity.Account;
import edu.nju.tickets.entity.Coupon;
import edu.nju.tickets.entity.User;
import edu.nju.tickets.entity.UserCoupon;
import edu.nju.tickets.service.MailService;
import edu.nju.tickets.service.UserService;
import edu.nju.tickets.util.Constants;
import edu.nju.tickets.util.DateTimeUtil;
import edu.nju.tickets.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static edu.nju.tickets.util.Constants.LEVEL_DISCOUNT;

@Service
public class UserServiceImpl implements UserService {

    private static final int INIT_POINTS = 0;   // 注册用户初始积分
    private static final int INIT_LEVEL = 1;    // 注册用户初始等级

    @Resource
    private UserDao userDao;
    @Resource
    private CouponDao couponDao;
    @Resource
    private UserCouponDao userCouponDao;
    @Resource
    private AccountDao accountDao;
    @Resource
    private OrderFormDao orderFormDao;

    @Resource
    private MailService mailService;

    private UserInfoVO convertUserToVO(User user) {
        if (user == null) {
            return null;
        }
        UserInfoVO vo = new UserInfoVO();
        vo.setEmail(user.getEmail());
        vo.setNickname(user.getNickname());
        vo.setLevel(user.getLevel());
        vo.setDiscount(LEVEL_DISCOUNT[user.getLevel()]);
        vo.setPoints(user.getPoints());
        vo.setRegisterTime(DateTimeUtil.convertTimestampToString(user.getRegisterTime()));
        return vo;
    }

    @Override
    public void sendVerificationCode(String email) throws SMTPAddressFailedException {
        mailService.sendVerificationCode(email);
    }

    /**
     * 用户注册时自动创建其账户，便于测试
     */
    private void generateAndSaveAccount(User user) {
        Account account = new Account();
        account.setUserId(user.getId());
        account.setAccountNumber(user.getEmail());
        account.setBalance(8888);
        account.setType("支付宝");
        accountDao.save(account);
    }

    @Override
    public void register(UserRegisterVO vo) {
        if (!mailService.verifyCode(vo.getEmail(), vo.getVerificationCode())) {
            throw new RuntimeException("验证码错误");
        }
        User user = userDao.findByEmail(vo.getEmail());
        if (user != null) {
            throw new RuntimeException("邮箱已被注册");
        }
        if (vo.getNickname() == null) {
            throw new RuntimeException("昵称不能为空");
        }
        user = new User();
        user.setEmail(vo.getEmail());
        user.setPassword(vo.getPassword());
        user.setNickname(vo.getNickname());
        user.setPoints(INIT_POINTS);
        user.setLevel(INIT_LEVEL);
        user.setRegisterTime(DateTimeUtil.getCurrentTimestamp());

        userDao.save(user);

        // 附带存储账户
        generateAndSaveAccount(user);
    }

    @Override
    public UserInfoVO login(String email, String password) {
        User user = userDao.findByEmailAndPassword(email, password);
        if (user == null || user.getLevel() < 0) {
            return null;
        }
        return convertUserToVO(user);
    }

    @Override
    public boolean cancel(String email) {
        User user = userDao.findByEmail(email);
        if (user == null || user.getLevel() < 0) {
            return false;
        }
        user.setLevel(-1);
        userDao.update(user);
        return true;
    }

    @Override
    public void changeUserInfo(String email, String nickname) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getLevel() < 0) {
            throw new RuntimeException("用户已被注销");
        }
        if (user.getNickname().equals(nickname)) {
            throw new RuntimeException("新昵称与原昵称相同");
        }

        user.setNickname(nickname);
        userDao.update(user);
    }

    @Override
    public UserInfoVO getUserInfo(String email) {
        return convertUserToVO(userDao.findByEmail(email));
    }

    @Override
    public void exchangeCoupon(String email, Integer couponId, int number) {
        User user = userDao.findByEmail(email);
        Coupon coupon = couponDao.get(couponId);
        if (user == null || coupon == null) {
            throw new RuntimeException("兑换失败");
        }
        if (user.getLevel() < 0) {
            throw new RuntimeException("用户已注销");
        }
        if (user.getPoints() < coupon.getRequirePoints() * number) {
            throw new RuntimeException("积分不够，兑换失败");
        }

        UserCoupon userCoupon = userCouponDao.findByUserIdAndCouponId(user.getId(), couponId);
        if (userCoupon == null) {
            userCoupon = new UserCoupon();
            userCoupon.setUserId(user.getId());
            userCoupon.setCouponId(couponId);
            userCoupon.setQuantity(number);
            userCouponDao.save(userCoupon);
        } else {
            userCoupon.setQuantity(userCoupon.getQuantity() + number);
            userCouponDao.update(userCoupon);
        }
        user.setPoints(user.getPoints() - coupon.getRequirePoints() * number);
        userDao.update(user);
    }

    private UserCouponVO convertCouponToVO(Integer userId, Coupon coupon) {
        if (userId == null || coupon == null) {
            return null;
        }
        UserCouponVO vo = new UserCouponVO();
        vo.setId(coupon.getId());
        vo.setMoney(coupon.getMoney());
        vo.setName(coupon.getName());
        vo.setRequirePoints(coupon.getRequirePoints());

        vo.setNumber(countCouponNumber(userId, coupon.getId()));

        return vo;
    }

    private int countCouponNumber(Integer userId, Integer couponId) {
        UserCoupon userCoupon = userCouponDao.findByUserIdAndCouponId(userId, couponId);
        return userCoupon == null ? 0 : userCoupon.getQuantity();
    }

    @Override
    public List<UserCouponVO> getUserCoupons(String email) {
        User user = userDao.findByEmail(email);
        if (user == null || user.getLevel() < 0) {
            return null;
        }
        Integer userId = user.getId();
        List<Coupon> coupons = couponDao.findAllOrderByMoney();
        return coupons
                .stream()
                .map(c -> convertCouponToVO(userId, c))
                .collect(Collectors.toList());
    }

    @Override
    public UsersStatisticsVO getUsersStatistics() {
        UsersStatisticsVO vo = new UsersStatisticsVO();

        List<Object[]> levelNumber = userDao.findLevelAndUserNumber();
        vo.setNumberList(levelNumber.stream().map(e -> (Long) e[0]).collect(Collectors.toList()));
        vo.setLevelList(levelNumber.stream().map(e -> (Integer) e[1]).collect(Collectors.toList()));

        return vo;
    }

    private double countRefundRatio(Integer userId) {
        long refundOrders       = orderFormDao.countByUserIdAndState(userId, Constants.OrderFormState.REFUND);
        long notAllocatedOrders = orderFormDao.countByUserIdAndState(userId, Constants.OrderFormState.NOT_ALLOCATED);
        long finishedOrders     = orderFormDao.countByUserIdAndState(userId, Constants.OrderFormState.FINISHED);

        return refundOrders * 1.0d / (refundOrders + notAllocatedOrders + finishedOrders);
    }

    @Override
    public IndividualStatisticsVO getIndividualStatistics(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        Integer id = user.getId();

        IndividualStatisticsVO vo = new IndividualStatisticsVO();

        double totalPoints = orderFormDao.sumTotalPriceByUserId(id) - orderFormDao.sumTotalPriceByUserIdAndState(id, Constants.OrderFormState.CANCELED);
        vo.setTotalPoints(totalPoints);
        vo.setCouponPoints((int)vo.getTotalPoints() - user.getPoints());
        vo.setRefundRatio(countRefundRatio(id));

        return vo;
    }

}
