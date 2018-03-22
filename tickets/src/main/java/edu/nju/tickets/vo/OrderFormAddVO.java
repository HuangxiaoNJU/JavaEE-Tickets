package edu.nju.tickets.vo;

import java.util.List;

public class OrderFormAddVO {

    private int purchaseType;
    private int projectPriceId;
    private int seatNumber;
    private Integer couponId;
    private List<String> seatList;

    public OrderFormAddVO() {}

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getProjectPriceId() {
        return projectPriceId;
    }

    public void setProjectPriceId(int projectPriceId) {
        this.projectPriceId = projectPriceId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public List<String> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<String> seatList) {
        this.seatList = seatList;
    }
}
