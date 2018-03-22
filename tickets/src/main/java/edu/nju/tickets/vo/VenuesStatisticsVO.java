package edu.nju.tickets.vo;

import java.util.List;

public class VenuesStatisticsVO {

    private List<String> venueList;
    private List<Double> incomeList;

    public VenuesStatisticsVO() {}

    public List<String> getVenueList() {
        return venueList;
    }

    public void setVenueList(List<String> venueList) {
        this.venueList = venueList;
    }

    public List<Double> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<Double> incomeList) {
        this.incomeList = incomeList;
    }
}
