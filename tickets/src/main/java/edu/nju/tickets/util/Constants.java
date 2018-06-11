package edu.nju.tickets.util;

import java.util.HashMap;

/**
 * 常量
 */
public class Constants {

    // 注册验证码长度
    public static final int VERIFICATION_CODE_LENGTH = 4;

    // 场馆识别码长度
    public static final int VENUE_IDENTIFICATION_LENGTH = 7;

    // 会员等级对应折扣
    public static final int[] LEVEL_DISCOUNT = new int[]{100, 98, 95, 92, 90, 85};
    // 会员升级积分阈值
    public static final int[] LEVEL_UP_THRESHOLD = new int[]{0, 100, 500, 1000, 2000, Integer.MAX_VALUE};

    /**
     * 访问者身份
     */
    public static class Identity {
        public static final String USER = "user";
        public static final String VENUE = "venue";
        public static final String MANAGER = "manager";
        public static final String VISITOR = "visitor";
    }

    /**
     * 不同身份Cookie名
     */
    public static class CookieName {
        public static final String USER_COOKIE_NAME = "email";
        public static final String VENUE_COOKIE_NAME = "identification";
        public static final String MANAGER_COOKIE_NAME = "manager_name";
    }

    /**
     * 活动进行状态
     */
    public static class ProjectState {
        public static final String NOT_BEGIN = "not_begin";
        public static final String UNDERWAY = "underway";
        public static final String FINISHED = "finished";
    }

    /**
     * 每多少分钟检查未支付订单
     */
    public static final int CHECK_NOT_PAID_ORDER_MINUTES = 5;
    /**
     * 每多少分钟检查未分配订单
     */
    public static final int CHECK_NOT_ALLOCATED_ORDER_MINUTES = 15;

    /**
     * 订单允许在多少分钟内进行支付
     */
    public static final int ORDER_ALLOW_PAY_MINUTES = 5;


    /**
     * 已配票订单退款返还金额占订单总价比例（单位：%）
     */
    public static final int ALLOCATED_ORDER_REFUND_RATIO = 50;

    /**
     * 未配票订单退款返还金额占订单总价比例（单位：%）
     */
    public static final int NOT_ALLOCATED_ORDER_REFUND_RATIO = 80;

    /**
     * 订单购买类型
     */
    public static class PurchaseType {
        public static final int CHOOSE_SEAT_PURCHASE = 0;   // 选座购买
        public static final int IMMEDIATE_PURCHASE   = 1;   // 立即购买

        public static final int CHOOSE_SEAT_PURCHASE_MAX_SEATS = 6;     // 选座购买最多一次性购买张数
        public static final int IMMEDIATE_PURCHASE_MAX_SEATS   = 20;    // 立即购买最多一次性购买张数
    }

    /**
     * 订单状态
     */
    public static class OrderFormState {
        public static final int NOT_PAYED       = 0;        // 待支付
        public static final int CANCELED        = 1;        // 已取消（15分钟未完成支付）
        public static final int REFUND          = 2;        // 已退款（配票不成功）
        public static final int NOT_ALLOCATED   = 3;        // 待分配
        public static final int FINISHED        = 4;        // 已完成

        public static final HashMap<Integer, String> ORDER_STATE_MAP = new HashMap<>();
        static {
            ORDER_STATE_MAP.put(NOT_PAYED, "待支付");
            ORDER_STATE_MAP.put(CANCELED, "已取消");
            ORDER_STATE_MAP.put(REFUND, "已退款");
            ORDER_STATE_MAP.put(NOT_ALLOCATED, "待分配");
            ORDER_STATE_MAP.put(FINISHED, "已完成");
        }
    }

}
