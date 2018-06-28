package edu.nju.tickets.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order_form")
public class OrderForm {
    private Integer id;
    private int purchaseType;
    private ProjectPrice projectPrice;
    private int seatNumber;
    private int state;
//    private User user;
    private Integer userId;
    private int discount;
    private double totalPrice;
    private Timestamp createTime;
    private Integer couponId;
    private boolean checkIn;
    private String seatList;
    private int score;

    public OrderForm() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "purchase_type")
    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_price_id", referencedColumnName = "id", nullable = false)
    public ProjectPrice getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(ProjectPrice projectPrice) {
        this.projectPrice = projectPrice;
    }

    @Column(name = "seat_number")
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Column(name = "total_price")
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "coupon_id")
    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    @Column(name = "check_in")
    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    @Column(name = "seat_list")
    public String getSeatList() {
        return seatList;
    }

    public void setSeatList(String seatList) {
        this.seatList = seatList;
    }

    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
