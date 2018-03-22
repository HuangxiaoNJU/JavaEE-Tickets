package edu.nju.tickets.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "allocation")
public class Allocation {
    private Integer id;
    private Integer venueId;
    private Integer projectId;
    private int ratio;
    private double platformIncome;
    private double venueIncome;
    private Timestamp time;

    public Allocation() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "venue_id")
    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    @Column(name = "project_id")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    @Column(name = "platform_income")
    public double getPlatformIncome() {
        return platformIncome;
    }

    public void setPlatformIncome(double platformIncome) {
        this.platformIncome = platformIncome;
    }

    @Column(name = "venue_income")
    public double getVenueIncome() {
        return venueIncome;
    }

    public void setVenueIncome(double venueIncome) {
        this.venueIncome = venueIncome;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
