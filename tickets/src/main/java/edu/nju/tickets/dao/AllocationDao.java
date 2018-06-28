package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Allocation;

public interface AllocationDao extends BaseDao<Allocation, Integer> {

    Allocation findByProjectId(Integer projectId);

    double sumVenueIncomeByVenueId(Integer venueId);

}
