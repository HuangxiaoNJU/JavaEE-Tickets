package edu.nju.tickets.vo;

import java.util.Map;

public class IndividualStatisticsVO {

    private double totalPoints;         // 积分总值
    private int couponPoints;           // 积分兑换优惠券总额
    private double averageEvaluation;   // 所有订单平均评价星级
    private double refundRatio;         // 退票率

    private Map<String, Double> consumePerDay;      // 日购票总金额
    private Map<String, Double> consumePerMonth;    // 月购票总金额

    public IndividualStatisticsVO() {}

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
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

    public double getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(double refundRatio) {
        this.refundRatio = refundRatio;
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
