package edu.nju.tickets.vo;

import java.util.Map;

public class IndividualStatisticsVO {

    private int totalPoints;            // 积分总值
    private int couponPoints;           // 积分兑换优惠券总额
    private double averageEvaluation;   // 所有订单平均评价星级
    private String refundRatio;         // 退票率

    private long payOrderNum;            // 付款订单总数
    private long refundOrderNum;         // 退款订单总数

    private Map<String, Double> consumePerDay;      // 日购票总金额统计
    private Map<String, Double> consumePerMonth;    // 月购票总金额统计
//    private Map<String, Double> consumePerYear;     // 月购票总金额统计

    public IndividualStatisticsVO() {}

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCouponPoints() {
        return couponPoints;
    }

    public void setCouponPoints(int couponPoints) {
        this.couponPoints = couponPoints;
    }

    public double getAverageEvaluation() {
        return averageEvaluation;
    }

    public void setAverageEvaluation(double averageEvaluation) {
        this.averageEvaluation = averageEvaluation;
    }

    public String getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(String refundRatio) {
        this.refundRatio = refundRatio;
    }

    public long getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(long payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public long getRefundOrderNum() {
        return refundOrderNum;
    }

    public void setRefundOrderNum(long refundOrderNum) {
        this.refundOrderNum = refundOrderNum;
    }

    public Map<String, Double> getConsumePerDay() {
        return consumePerDay;
    }

    public void setConsumePerDay(Map<String, Double> consumePerDay) {
        this.consumePerDay = consumePerDay;
    }

    public Map<String, Double> getConsumePerMonth() {
        return consumePerMonth;
    }

    public void setConsumePerMonth(Map<String, Double> consumePerMonth) {
        this.consumePerMonth = consumePerMonth;
    }
}
