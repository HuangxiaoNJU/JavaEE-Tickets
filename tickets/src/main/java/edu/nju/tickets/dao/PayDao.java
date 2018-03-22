package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Pay;

import java.util.List;

public interface PayDao extends BaseDao<Pay, Integer> {

    double sumMoneyByProjectIdAndIsOnline(Integer projectId, boolean isOnline);

    double sumMoneyByProjectId(Integer projectId);

    double sumMoneyByVenueIdAndIsOnline(Integer venueId, boolean isOnline);

    double sumMoneyByVenueId(Integer venueId);

    List<Object[]> findProjectIncomeByVenueId(Integer venueId);

}
