package edu.nju.tickets.entity;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
public class Coupon {
    private Integer id;
    private String name;        // 优惠券名称
    private int money;          // 优惠金额
    private int requirePoints;  // 兑换所需积分

    public Coupon() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Column(name = "require_points")
    public int getRequirePoints() {
        return requirePoints;
    }

    public void setRequirePoints(int requirePoints) {
        this.requirePoints = requirePoints;
    }
}
