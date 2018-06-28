package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Allocation;

import java.util.List;

public interface AllocationDao extends BaseDao<Allocation, Integer> {

    Allocation findByProjectId(Integer projectId);

    double sumVenueIncomeByVenueId(Integer venueId);

    List<Object[]> sumPlatformIncomeGroupByProjectTypeAndDay();

    List<Object[]> sumPlatformIncomeGroupByProjectTypeAndMonth();

    List<Object[]> sumPlatformIncomeGroupByProjectTypeAndYear();

}
