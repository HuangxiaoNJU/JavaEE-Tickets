package edu.nju.tickets.vo;

import java.util.Map;

public class VenueIndividualStatistics {

    private int totalProjectNum;                    // 总活动数

    private Map<String, Double> profitPerDay;       // 场馆日收益
    private Map<String, Double> profitPerMonth;     // 场馆月收益
    private Map<String, Double> profitPerYear;      // 场馆年收益

    private String presentRatio;                    // 平均上座率
    private String refundRatio;                     // 平均退票率

    public VenueIndividualStatistics() {}

    public int getTotalProjectNum() {
        return totalProjectNum;
    }

    public void setTotalProjectNum(int totalProjectNum) {
        this.totalProjectNum = totalProjectNum;
    }

    public Map<String, Double> getProfitPerDay() {
        return profitPerDay;
    }

    public void setProfitPerDay(Map<String, Double> profitPerDay) {
        this.profitPerDay = profitPerDay;
    }

    public Map<String, Double> getProfitPerMonth() {
        return profitPerMonth;
    }

    public void setProfitPerMonth(Map<String, Double> profitPerMonth) {
        this.profitPerMonth = profitPerMonth;
    }

    public Map<String, Double> getProfitPerYear() {
        return profitPerYear;
    }

    public void setProfitPerYear(Map<String, Double> profitPerYear) {
        this.profitPerYear = profitPerYear;
    }

    public String getPresentRatio() {
        return presentRatio;
    }

    public void setPresentRatio(String presentRatio) {
        this.presentRatio = presentRatio;
    }

    public String getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(String refundRatio) {
        this.refundRatio = refundRatio;
    }
}
