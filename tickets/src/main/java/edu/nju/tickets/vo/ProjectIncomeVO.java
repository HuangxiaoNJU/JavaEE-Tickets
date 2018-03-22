package edu.nju.tickets.vo;

public class ProjectIncomeVO {

    private ProjectInfoVO projectInfoVO;
    private double onlineIncome;
    private double offlineIncome;
    private boolean isAllocated;
    private int ratio;
    private double platformIncome;
    private double venueIncome;

    public ProjectIncomeVO() {}

    public ProjectInfoVO getProjectInfoVO() {
        return projectInfoVO;
    }

    public void setProjectInfoVO(ProjectInfoVO projectInfoVO) {
        this.projectInfoVO = projectInfoVO;
    }

    public double getOnlineIncome() {
        return onlineIncome;
    }

    public void setOnlineIncome(double onlineIncome) {
        this.onlineIncome = onlineIncome;
    }

    public double getOfflineIncome() {
        return offlineIncome;
    }

    public void setOfflineIncome(double offlineIncome) {
        this.offlineIncome = offlineIncome;
    }

    public boolean isAllocated() {
        return isAllocated;
    }

    public void setAllocated(boolean allocated) {
        isAllocated = allocated;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public double getPlatformIncome() {
        return platformIncome;
    }

    public void setPlatformIncome(double platformIncome) {
        this.platformIncome = platformIncome;
    }

    public double getVenueIncome() {
        return venueIncome;
    }

    public void setVenueIncome(double venueIncome) {
        this.venueIncome = venueIncome;
    }
}
