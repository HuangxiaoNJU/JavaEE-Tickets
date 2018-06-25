package edu.nju.tickets.vo;

public class IndividualStatisticsVO {

    private double totalPoints;         // 积分总值
    private int couponPoints;           // 积分兑换优惠券总额
    private double averageEvaluation;   // 所有订单平均评价星级
    private double refundRatio;         // 退票率

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
}
