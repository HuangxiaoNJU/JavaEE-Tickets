package edu.nju.tickets.vo;

import java.util.List;

public class ProjectInfoVO {

    private Integer id;
    private String name;
    private String description;
    private String beginTime;
    private String endTime;
    private String type;
    private int totalPeople;
    private VenueInfoVO venueInfoVO;
    private List<PriceInfoVO> prices;

    public ProjectInfoVO() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public VenueInfoVO getVenueInfoVO() {
        return venueInfoVO;
    }

    public void setVenueInfoVO(VenueInfoVO venueInfoVO) {
        this.venueInfoVO = venueInfoVO;
    }

    public List<PriceInfoVO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceInfoVO> prices) {
        this.prices = prices;
    }
}
