package edu.nju.tickets.vo;

public class VenueModifyInfoVO {

    private Integer id;
    private VenueInfoVO venueInfoVO;
    private String newName;
    private Integer newSeatNumber;
    private String newLocation;
    private String applyTime;
    private boolean isChecked;
    private boolean isPass;

    public VenueModifyInfoVO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VenueInfoVO getVenueInfoVO() {
        return venueInfoVO;
    }

    public void setVenueInfoVO(VenueInfoVO venueInfoVO) {
        this.venueInfoVO = venueInfoVO;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Integer getNewSeatNumber() {
        return newSeatNumber;
    }

    public void setNewSeatNumber(Integer newSeatNumber) {
        this.newSeatNumber = newSeatNumber;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }
}
