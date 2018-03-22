package edu.nju.tickets.vo;

import java.util.List;

public class VenueStatisticsVO {

    private double income;
    private double profit;

    private double onlineIncome;
    private double offlineIncome;

    private List<String> projectNameList;
    private List<Double> projectIncomeList;

    public VenueStatisticsVO() {}

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getOnlineIncome() {
        return onlineIncome;
    }

    public void setOnlineIncome(double onlineIncome) {
        this.onlineIncome = onlineIncome;
    }

    public double getOfflineIncome() {
        return offlineIncome;
    }

    public void setOfflineIncome(double offlineIncome) {
        this.offlineIncome = offlineIncome;
    }

    public List<String> getProjectNameList() {
        return projectNameList;
    }

    public void setProjectNameList(List<String> projectNameList) {
        this.projectNameList = projectNameList;
    }

    public List<Double> getProjectIncomeList() {
        return projectIncomeList;
    }

    public void setProjectIncomeList(List<Double> projectIncomeList) {
        this.projectIncomeList = projectIncomeList;
    }
}
