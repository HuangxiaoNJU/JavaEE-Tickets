package edu.nju.tickets.service.impl;

import edu.nju.tickets.dao.*;
import edu.nju.tickets.entity.*;
import edu.nju.tickets.service.OrderFormService;
import edu.nju.tickets.service.OrderFormStateChange;
import edu.nju.tickets.service.ProjectInfo;
import edu.nju.tickets.util.DateTimeUtil;
import edu.nju.tickets.vo.CouponVO;
import edu.nju.tickets.vo.OrderFormAddVO;
import edu.nju.tickets.vo.OrderFormInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static edu.nju.tickets.util.Constants.*;
import static edu.nju.tickets.util.Constants.OrderFormState.*;
import static edu.nju.tickets.util.Constants.PurchaseType.*;

@Service
public class OrderFormServiceImpl implements OrderFormService, OrderFormStateChange {

    @Resource
    private OrderFormDao orderFormDao;

    @Resource
    private UserDao userDao;
    @Resource
    private UserCouponDao userCouponDao;
    @Resource
    private CouponDao couponDao;
    @Resource
    private AccountDao accountDao;
    @Resource
    private ProjectPriceDao projectPriceDao;
    @Resource
    private PayDao payDao;
    @Resource
    private VenueDao venueDao;

    @Resource
    private ProjectInfo projectInfo;

    private OrderFormInfoVO convertOrderFormToVO(OrderForm orderForm) {
        if (orderForm == null) {
            return null;
        }
        OrderFormInfoVO vo = new OrderFormInfoVO();
        vo.setId(orderForm.getId());
        vo.setPurchaseType(orderForm.getPurchaseType() == CHOOSE_SEAT_PURCHASE ? "选座购买" : "立即购买");
        vo.setSeatNumber(orderForm.getSeatNumber());
        vo.setDiscount(orderForm.getDiscount());
        vo.setState(ORDER_STATE_MAP.get(orderForm.getState()));
        vo.setCreateTime(DateTimeUtil.convertTimestampToString(orderForm.getCreateTime()));

        // 价格精确到小数点后两位
        String totalPrice = String.valueOf((int)(orderForm.getTotalPrice() * 100) / 100.0d);
        vo.setTotalPrice(totalPrice);

        ProjectPrice price = orderForm.getProjectPrice();
        vo.setPriceInfoVO(projectInfo.convertProjectPriceToVO(price));
        vo.setProjectInfoVO(projectInfo.convertProjectToVO(price.getProject()));

        // 使用优惠券
        if (orderForm.getCouponId() != 0) {
            Coupon coupon = couponDao.get(orderForm.getCouponId());
            vo.setCouponVO(convertCouponToVO(coupon));
        }
        if (orderForm.getSeatList() != null) {
            vo.setSeatList(Arrays.asList(orderForm.getSeatList().split(",")));
        }
        vo.setScore(orderForm.getScore());
        return vo;
    }

    private CouponVO convertCouponToVO(Coupon coupon) {
        if (coupon == null) {
            return null;
        }
        CouponVO vo = new CouponVO();
        vo.setId(coupon.getId());
        vo.setMoney(coupon.getMoney());
        vo.setName(coupon.getName());
        vo.setRequirePoints(coupon.getRequirePoints());
        return vo;
    }

    @Override
    public OrderFormInfoVO getOrderFormInfoById(Integer id) {
        OrderForm orderForm = orderFormDao.get(id);
        return convertOrderFormToVO(orderForm);
    }

    @Override
    public List<OrderFormInfoVO> getOrderFormsByUser(String email, int state) {
        User user = userDao.findByEmail(email);
        List<OrderForm> orderForms;
        if (state < 0) {
            orderForms = orderFormDao.findByUserIdOrderByCreateTimeDesc(user.getId());
        } else {
            orderForms = orderFormDao.findByUserIdAndStateOrderByCreateTimeDesc(user.getId(), state);
        }
        return orderForms
                .stream()
                .map(this::convertOrderFormToVO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderFormInfoVO makeOrderFormOffline(String identification, OrderFormAddVO vo) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            throw new RuntimeException("场馆不存在");
        }
        ProjectPrice projectPrice = projectPriceDao.get(vo.getProjectPriceId());
        if (projectPrice == null) {
            throw new RuntimeException("购票错误");
        }
        if (!projectPrice.getProject().getVenue().getId().equals(venue.getId())) {
            throw new RuntimeException("非本场馆活动，不可线下购票");
        }

        Project project = projectPrice.getProject();
        if (project.getBeginTime().compareTo(new Date()) <= 0) {
            throw new RuntimeException("错过购票时间");
        }
        if (vo.getSeatNumber() <= 0 || vo.getSeatNumber() > CHOOSE_SEAT_PURCHASE_MAX_SEATS) {
            throw new RuntimeException("座位张数错误");
        }

        OrderForm orderForm = new OrderForm();

        orderForm.setPurchaseType(CHOOSE_SEAT_PURCHASE);
        // 线下支付订单即刻为已完成
        orderForm.setState(FINISHED);
        orderForm.setSeatNumber(vo.getSeatNumber());
        orderForm.setDiscount(100);
        // 设定订单创建时间为当前时间
        orderForm.setCreateTime(DateTimeUtil.getCurrentTimestamp());

        double totalPrice = (int)(projectPrice.getPrice() * vo.getSeatNumber() * 100) / 100.0d;
        orderForm.setTotalPrice(totalPrice);
        orderForm.setProjectPrice(projectPrice);
        // 选座购票设置座位（座位号间以英文逗号分隔）
        orderForm.setSeatList(vo.getSeatList().stream().reduce((s1, s2) -> s1 + "," + s2).get());
        // 设置userId为0表示线下直接购买
        orderForm.setUserId(0);
        orderForm.setCouponId(0);
        orderForm.setCheckIn(true);
        orderFormDao.save(orderForm);

        Pay pay = new Pay();
        pay.setVenueId(projectPrice.getProject().getVenue().getId());
        pay.setProjectId(projectPrice.getProject().getId());
        pay.setMoney(totalPrice);
        pay.setTime(DateTimeUtil.getCurrentTimestamp());
        pay.setOnline(false);
        payDao.save(pay);

        return convertOrderFormToVO(orderForm);
    }

    @Override
    public OrderFormInfoVO makeOrderForm(String email, OrderFormAddVO vo) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (vo.getSeatNumber() <= 0) {
            throw new RuntimeException("至少购买一张座位");
        }
        if (vo.getPurchaseType() == CHOOSE_SEAT_PURCHASE) {
            if (vo.getSeatNumber() > CHOOSE_SEAT_PURCHASE_MAX_SEATS) {
                throw new RuntimeException("选座购买最多一次只可买" + CHOOSE_SEAT_PURCHASE_MAX_SEATS + "张");
            }
            if (vo.getSeatList() == null || vo.getSeatList().isEmpty() || vo.getSeatList().size() != vo.getSeatNumber()) {
                throw new RuntimeException("选择座位错误");
            }
        }
        if (vo.getPurchaseType() == IMMEDIATE_PURCHASE &&
                vo.getSeatNumber() > IMMEDIATE_PURCHASE_MAX_SEATS) {
            throw new RuntimeException("立即购买最多一次只可买" + CHOOSE_SEAT_PURCHASE_MAX_SEATS + "张");
        }

        ProjectPrice projectPrice = projectPriceDao.get(vo.getProjectPriceId());
        if (projectPrice == null) {
            throw new RuntimeException("购票错误");
        }
        Project project = projectPrice.getProject();
        if (project.getBeginTime().compareTo(new Date()) <= 0) {
            throw new RuntimeException("错过购票时间");
        }

        OrderForm orderForm = new OrderForm();

        // 计算优惠券优惠价格
        int couponMoney = 0;
        if (vo.getCouponId() != null && vo.getCouponId() != 0) {
            UserCoupon userCoupon = userCouponDao.findByUserIdAndCouponId(user.getId(), vo.getCouponId());
            if (userCoupon == null || userCoupon.getQuantity() < 1) {
                throw new RuntimeException("优惠券不足");
            }
            Coupon coupon = couponDao.get(vo.getCouponId());
            couponMoney = coupon.getMoney();

            orderForm.setCouponId(coupon.getId());
        } else {
            orderForm.setCouponId(0);
        }

        // 获取用户会员等级对应折扣
        int discount = LEVEL_DISCOUNT[user.getLevel()];
        // 计算订单总价
        double totalPrice = vo.getSeatNumber() * projectPrice.getPrice() * discount * 0.01 - couponMoney;
        // 精确到小数点后两位
        totalPrice = ((int) (totalPrice * 100)) / 100.0;
        totalPrice = totalPrice < 0 ? 0 : totalPrice;

        orderForm.setPurchaseType(vo.getPurchaseType());
        orderForm.setState(NOT_PAYED);
        orderForm.setSeatNumber(vo.getSeatNumber());
        orderForm.setUserId(user.getId());
        orderForm.setDiscount(discount);
        // 设定订单创建时间为当前时间
        orderForm.setCreateTime(DateTimeUtil.getCurrentTimestamp());
        orderForm.setTotalPrice(totalPrice);
        orderForm.setProjectPrice(projectPrice);
        // 选座购票设置座位（座位号间以英文逗号分隔）
        if (vo.getPurchaseType() == CHOOSE_SEAT_PURCHASE) {
            orderForm.setSeatList(vo.getSeatList().stream().reduce((s1, s2) -> s1 + "," + s2).get());
        }
        orderForm.setScore(-1);
        orderFormDao.save(orderForm);

        return convertOrderFormToVO(orderForm);
    }

    @Override
    public Double getRealTimeOrderFormPrice(String email, OrderFormAddVO vo) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            return null;
        }
        if (vo.getSeatNumber() == 0) {
            return 0.0d;
        }
        ProjectPrice projectPrice = projectPriceDao.get(vo.getProjectPriceId());
        if (projectPrice == null) {
            return null;
        }
        // 计算优惠券优惠价格
        int couponMoney = 0;
        if (vo.getCouponId() != null && vo.getCouponId() != 0) {
            UserCoupon userCoupon = userCouponDao.findByUserIdAndCouponId(user.getId(), vo.getCouponId());
            if (userCoupon == null || userCoupon.getQuantity() < 1) {
                return null;
            }
            Coupon coupon = couponDao.get(vo.getCouponId());
            couponMoney = coupon.getMoney();
        }

        // 获取用户会员等级对应折扣
        int discount = LEVEL_DISCOUNT[user.getLevel()];
        // 计算订单总价
        double totalPrice = vo.getSeatNumber() * projectPrice.getPrice() * discount * 0.01 - couponMoney;
        // 精确到小数点后两位
        totalPrice = (int) (totalPrice * 100) / 100.0d;
        totalPrice = Double.max(0, totalPrice);

        return totalPrice;
    }

    @Override
    public void payForOrderForm(String email, Integer accountId, Integer orderFormId) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        Account account = accountDao.get(accountId);
        if (!account.getUserId().equals(user.getId())) {
            throw new RuntimeException("账户非用户所有");
        }
        OrderForm orderForm = orderFormDao.get(orderFormId);
        if (orderForm == null) {
            throw new RuntimeException("订单不存在");
        }
        if (orderForm.getState() != NOT_PAYED) {
            throw new RuntimeException("订单不可支付");
        }
        if (orderForm.getTotalPrice() > account.getBalance()) {
            throw new RuntimeException("余额不足，支付失败");
        }
        // 若订单使用优惠券，则更新用户优惠券余量
        if (orderForm.getCouponId() != 0) {
            UserCoupon userCoupon = userCouponDao.findByUserIdAndCouponId(user.getId(), orderForm.getCouponId());
            if (userCoupon == null || userCoupon.getQuantity() == 0) {
                throw new RuntimeException("优惠券不足");
            }
            userCoupon.setQuantity(userCoupon.getQuantity() - 1);
            userCouponDao.update(userCoupon);
        }

        // 更新账户余额
        account.setBalance(account.getBalance() - orderForm.getTotalPrice());
        accountDao.update(account);
        // 更新用户积分
        user.setPoints(user.getPoints() + (int)orderForm.getTotalPrice());
        // 更新会员等级
        for (int i = 0; i < LEVEL_UP_THRESHOLD.length - 1; i++) {
            if (user.getPoints() >= LEVEL_UP_THRESHOLD[i] && user.getPoints() < LEVEL_UP_THRESHOLD[i + 1]) {
                user.setLevel(i + 1);
                break;
            }
        }
        userDao.update(user);

        // 修改订单状态
        if (orderForm.getPurchaseType() == CHOOSE_SEAT_PURCHASE) {
            // 选座购买，订单支付后即已完成
            orderForm.setState(FINISHED);
        } else {
            // 立即购买，订单支付后等待分配座位
            orderForm.setState(NOT_ALLOCATED);
        }
        orderFormDao.update(orderForm);

        // 记录支付信息
        Pay pay = new Pay();
        pay.setVenueId(orderForm.getProjectPrice().getProject().getVenue().getId());
        pay.setProjectId(orderForm.getProjectPrice().getProject().getId());
        pay.setMoney(orderForm.getTotalPrice());
        pay.setTime(DateTimeUtil.getCurrentTimestamp());
        pay.setOnline(true);
        payDao.save(pay);
    }

    @Override
    public double refundOrderForm(String email, Integer orderFormId) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        OrderForm orderForm = orderFormDao.get(orderFormId);
        if (orderForm == null) {
            throw new RuntimeException("订单不存在");
        }
        Project project = orderForm.getProjectPrice().getProject();
        if (project.getBeginTime().compareTo(DateTimeUtil.getCurrentTimestamp()) < 0) {
            throw new RuntimeException("活动已开始，不可退款");
        }

        double refundMoney;
        switch (orderForm.getState()) {
            case NOT_ALLOCATED:     // 未配票订单退款
                refundMoney = (int)(orderForm.getTotalPrice() * NOT_ALLOCATED_ORDER_REFUND_RATIO) / 100.0d;
                break;
            case FINISHED:          // 已配票订单退款
                refundMoney = (int)(orderForm.getTotalPrice() * ALLOCATED_ORDER_REFUND_RATIO) / 100.0d;
                break;
            default:
                throw new RuntimeException(ORDER_STATE_MAP.get(orderForm.getState()) + "订单不可退款");
        }
        // 更新订单状态
        orderForm.setState(REFUND);
        orderFormDao.update(orderForm);

        // 更新用户积分
        user.setPoints(user.getPoints() - (int)refundMoney);
        // 更新会员等级
        for (int i = 0; i < LEVEL_UP_THRESHOLD.length - 1; i++) {
            if (user.getPoints() >= LEVEL_UP_THRESHOLD[i] && user.getPoints() < LEVEL_UP_THRESHOLD[i + 1]) {
                user.setLevel(i + 1);
                break;
            }
        }
        userDao.update(user);

        // 记录退款信息
        Pay pay = new Pay();
        pay.setVenueId(project.getVenue().getId());
        pay.setProjectId(project.getId());
        pay.setMoney(-refundMoney);
        pay.setTime(DateTimeUtil.getCurrentTimestamp());
        pay.setOnline(true);
        payDao.save(pay);

        return refundMoney;
    }

    @Override
    public void checkIn(String identification, Integer orderId) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            throw new RuntimeException("场馆不存在");
        }
        OrderForm orderForm = orderFormDao.get(orderId);
        if (orderForm == null) {
            throw new RuntimeException("订单不存在");
        }
        if (!orderForm.getProjectPrice().getProject().getVenue().getId().equals(venue.getId())) {
            throw new RuntimeException("非本场馆订单，不可检票");
        }
        if (orderForm.isCheckIn()) {
            throw new RuntimeException("订单已检票，不可重复检票");
        }
        if (orderForm.getState() != FINISHED) {
            throw new RuntimeException(ORDER_STATE_MAP.get(orderForm.getState()) + "订单不可检票");
        }
        if (orderForm.getProjectPrice().getProject().getEndTime().compareTo(DateTimeUtil.getCurrentTimestamp()) < 0) {
            throw new RuntimeException("活动已结束，不可检票");
        }
        orderForm.setCheckIn(true);
        orderFormDao.update(orderForm);
    }

    /**
     * 订单是否超过时间未完成支付
     */
    private boolean isExceedPayTime(OrderForm orderForm) {
        Timestamp currentTime = DateTimeUtil.getCurrentTimestamp();
        Timestamp createTime = orderForm.getCreateTime();
        return currentTime.getTime() - createTime.getTime() > ORDER_ALLOW_PAY_MINUTES * 60 * 1000;
    }

    @Override
    public void convertNotPaidToCanceled() {
        orderFormDao
                .findByState(NOT_PAYED)
                .stream()
                .filter(this::isExceedPayTime)
                .forEach(o -> {
                    o.setState(CANCELED);
                    orderFormDao.update(o);
                    System.out.println("订单" + o.getId() + "因长时间未支付取消");
                });
    }

    @Override
    public void convertNotAllocatedToFinished() {
        List<OrderForm> orderForms = orderFormDao.findByState(NOT_ALLOCATED);
        for (OrderForm orderForm : orderForms) {
            ProjectPrice projectPrice = orderForm.getProjectPrice();
            List<String> allocateSeats = new ArrayList<>();
            int toAllocate = orderForm.getSeatNumber();
            // 获取不可分配座位
            List<String> unavailableList = projectInfo.convertProjectPriceToVO(projectPrice).getUnavailableList();
            // 顺次挑选座位分配
            L1: for (int i = 1; i <= projectPrice.getRow(); i++) {
                for (int j = 1; j <= projectPrice.getColumn(); j++) {
                    if (!unavailableList.contains(i + "_" + j)) {
                        allocateSeats.add(i + "_" + j);
                        toAllocate--;
                        if (toAllocate == 0) {
                            break L1;
                        }
                    }
                }
            }

            if (toAllocate == 0) {
                orderForm.setSeatList(allocateSeats.stream().reduce((s1, s2) -> s1 + "," + s2).get());
                orderForm.setState(FINISHED);
                orderFormDao.update(orderForm);
                System.out.println("订单" + orderForm.getId() + "已分配完成，座位号：" + orderForm.getSeatList());
            }
        }
    }

    @Override
    public Boolean updateOrderFormScore(Integer orderId,int score) {
        OrderForm orderForm = orderFormDao.get(orderId);
        try {
            if(orderForm.getScore()==-1) {
                orderForm.setScore(score);
                orderFormDao.update(orderForm);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
