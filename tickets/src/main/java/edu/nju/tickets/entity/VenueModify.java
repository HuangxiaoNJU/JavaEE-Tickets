package edu.nju.tickets.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "venue_modify")
public class VenueModify {

    private Integer id;
    private Venue venue;
    private String newName;
    private Integer newSeatNumber;
    private String newLocation;
    private Timestamp applyTime;
    private boolean isChecked;
    private boolean isPass;

    public VenueModify() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = Venue.class, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Column(name = "new_name")
    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Column(name = "new_seat_number")
    public Integer getNewSeatNumber() {
        return newSeatNumber;
    }

    public void setNewSeatNumber(Integer newSeatNumber) {
        this.newSeatNumber = newSeatNumber;
    }

    @Column(name = "new_location")
    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    @Column(name = "apply_time")
    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    @Column(name = "is_checked")
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Column(name = "is_pass")
    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }
}
