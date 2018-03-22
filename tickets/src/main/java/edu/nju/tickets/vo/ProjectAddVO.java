package edu.nju.tickets.vo;

import java.util.List;

public class ProjectAddVO {
    private String name;
    private String beginTime;
    private String endTime;
    private String type;
    private String description;
    private List<ProjectPriceVO> prices;

    public ProjectAddVO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectPriceVO> getPrices() {
        return prices;
    }

    public void setPrices(List<ProjectPriceVO> prices) {
        this.prices = prices;
    }
}
