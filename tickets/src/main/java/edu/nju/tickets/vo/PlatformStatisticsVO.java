package edu.nju.tickets.vo;

import java.util.Map;

public class PlatformStatisticsVO {

    private Map<String, Double> profitPerDay;                       // 平台日分成总额
    private Map<String, Double> profitPerMonth;                     // 平台月度分成总额
    private Map<String, Double> profitPerYear;                      // 平台年度分成总额

    private String exchangeRatio;                                   // 积分兑换率（百分数）

    private Map<String, Map<String, Double>> typeProfitPerDay;      // 不同类型活动日分成总额
    private Map<String, Map<String, Double>> typeProfitPerMonth;    // 不同类型活动月分成总额
    private Map<String, Map<String, Double>> typeProfitPerYear;     // 不同类型活动年分成总额

    private Map<String, String> soldRatio;                          // 各场馆上座率

    public PlatformStatisticsVO() {}

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

    public String getExchangeRatio() {
        return exchangeRatio;
    }

    public void setExchangeRatio(String exchangeRatio) {
        this.exchangeRatio = exchangeRatio;
    }

    public Map<String, Map<String, Double>> getTypeProfitPerDay() {
        return typeProfitPerDay;
    }

    public void setTypeProfitPerDay(Map<String, Map<String, Double>> typeProfitPerDay) {
        this.typeProfitPerDay = typeProfitPerDay;
    }

    public Map<String, Map<String, Double>> getTypeProfitPerMonth() {
        return typeProfitPerMonth;
    }

    public void setTypeProfitPerMonth(Map<String, Map<String, Double>> typeProfitPerMonth) {
        this.typeProfitPerMonth = typeProfitPerMonth;
    }

    public Map<String, Map<String, Double>> getTypeProfitPerYear() {
        return typeProfitPerYear;
    }

    public void setTypeProfitPerYear(Map<String, Map<String, Double>> typeProfitPerYear) {
        this.typeProfitPerYear = typeProfitPerYear;
    }

    public Map<String, String> getSoldRatio() {
        return soldRatio;
    }

    public void setSoldRatio(Map<String, String> soldRatio) {
        this.soldRatio = soldRatio;
    }
}
