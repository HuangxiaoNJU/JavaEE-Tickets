package edu.nju.tickets.vo;

import java.util.List;

public class UsersStatisticsVO {
    private List<Integer> levelList;    // 会员等级
    private List<Long> numberList;      // 对应人数

    public UsersStatisticsVO() {}

    public List<Integer> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Integer> levelList) {
        this.levelList = levelList;
    }

    public List<Long> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<Long> numberList) {
        this.numberList = numberList;
    }
}
