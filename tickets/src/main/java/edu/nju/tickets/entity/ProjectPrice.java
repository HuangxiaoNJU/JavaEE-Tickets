package edu.nju.tickets.entity;

import javax.persistence.*;

@Entity
@Table(name = "project_price")
public class ProjectPrice {
    private Integer id;
    private Project project;
    private String seatName;
    private int seatNumber;
    private double price;
    private int row;
    private int column;

    public ProjectPrice() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "seat_name")
    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    @Column(name = "seat_number")
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

    @Column(name = "row_num")
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Column(name = "column_num")
    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
