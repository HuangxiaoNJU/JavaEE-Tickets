package edu.nju.tickets.vo;

import java.util.List;

public class PriceInfoVO {

    private Integer id;
    private String seatName;
    private int seatNumber;
    private double price;
    private int row;
    private int column;
    private List<String> unavailableList;

    public PriceInfoVO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<String> getUnavailableList() {
        return unavailableList;
    }

    public void setUnavailableList(List<String> unavailableList) {
        this.unavailableList = unavailableList;
    }
}
