package edu.nju.tickets.vo;

public class VenueChangeVO {

    private String newName;
    private int newSeatNumber;
    private String newLocation;

    public VenueChangeVO() {}

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public int getNewSeatNumber() {
        return newSeatNumber;
    }

    public void setNewSeatNumber(int newSeatNumber) {
        this.newSeatNumber = newSeatNumber;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }
}
