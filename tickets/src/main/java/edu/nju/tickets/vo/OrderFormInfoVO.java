package edu.nju.tickets.vo;

import java.util.List;

public class OrderFormInfoVO {

    private Integer id;
    private String purchaseType;
    private String state;
    private ProjectInfoVO projectInfoVO;
    private PriceInfoVO priceInfoVO;
    private String totalPrice;
    private int seatNumber;
    private int discount;
    private String createTime;
    private CouponVO couponVO;
    private List<String> seatList;

    public OrderFormInfoVO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ProjectInfoVO getProjectInfoVO() {
        return projectInfoVO;
    }

    public void setProjectInfoVO(ProjectInfoVO projectInfoVO) {
        this.projectInfoVO = projectInfoVO;
    }

    public PriceInfoVO getPriceInfoVO() {
        return priceInfoVO;
    }

    public void setPriceInfoVO(PriceInfoVO priceInfoVO) {
        this.priceInfoVO = priceInfoVO;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public CouponVO getCouponVO() {
        return couponVO;
    }

    public void setCouponVO(CouponVO couponVO) {
        this.couponVO = couponVO;
    }

    public List<String> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<String> seatList) {
        this.seatList = seatList;
    }
}
