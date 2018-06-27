package edu.nju.tickets.service.impl;

import com.sun.mail.smtp.SMTPAddressFailedException;
import edu.nju.tickets.dao.*;
import edu.nju.tickets.entity.*;
import edu.nju.tickets.service.MailService;
import edu.nju.tickets.service.UserService;
import edu.nju.tickets.util.Constants;
import edu.nju.tickets.util.DateTimeUtil;
import edu.nju.tickets.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static edu.nju.tickets.util.Constants.LEVEL_DISCOUNT;
import static edu.nju.tickets.util.Constants.NUMBER_FORMAT;
import static edu.nju.tickets.util.Constants.OrderFormState.CANCELED;
import static edu.nju.tickets.util.Constants.OrderFormState.NOT_PAYED;

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

    private void countUserOrdersByState(Integer userId, IndividualStatisticsVO vo) {
        long refundOrders       = orderFormDao.countByUserIdAndState(userId, Constants.OrderFormState.REFUND);
        long notAllocatedOrders = orderFormDao.countByUserIdAndState(userId, Constants.OrderFormState.NOT_ALLOCATED);
        long finishedOrders     = orderFormDao.countByUserIdAndState(userId, Constants.OrderFormState.FINISHED);

        vo.setRefundOrderNum(refundOrders);

        long payOrderNum = refundOrders + notAllocatedOrders + finishedOrders;
        vo.setPayOrderNum(finishedOrders + refundOrders + notAllocatedOrders);

        double ratio = 0;
        if (payOrderNum != 0) {
            ratio = refundOrders * 1.0d / (refundOrders + notAllocatedOrders + finishedOrders);
        }
        vo.setRefundRatio(NUMBER_FORMAT.format(ratio));
    }

    /**
     * 获取用户消费按年、月、日分别统计的信息
     *
     * @param orders        用户所有订单（已按时间排好序）
     * @param endIndex      "yyyy-MM-dd"中结束字符后一个位置下标，按年统计为4，按月统计为7，按日统计为10
     * @return              map
     */
    private Map<String, Double> getConsumeMap(List<OrderForm> orders, int endIndex) {
        return orders.stream()
                // 剔除未支付订单及已取消订单
                .filter(o -> o.getState() != NOT_PAYED && o.getState() != CANCELED)
                .collect(
                        Collectors.groupingBy(
                                o -> DateTimeUtil.convertTimestampToString(o.getCreateTime()).substring(0, endIndex),
                                TreeMap::new,
                                Collectors.summingDouble(OrderForm::getTotalPrice)
                        )
                );
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
        vo.setTotalPoints((int) totalPoints);

        countUserOrdersByState(id, vo);

        List<OrderForm> orders = orderFormDao.findByUserIdOrderByCreateTimeDesc(id);
        vo.setConsumePerDay(getConsumeMap(orders, 10));
        vo.setConsumePerMonth(getConsumeMap(orders, 7));

        return vo;
    }

}
