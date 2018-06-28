package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.AllocationDao;
import edu.nju.tickets.entity.Allocation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllocationDaoImpl extends BaseDaoImpl<Allocation, Integer> implements AllocationDao {

    @Override
    public Allocation findByProjectId(Integer projectId) {
        List<Allocation> allocations = find("from Allocation a where a.projectId=?", projectId);
        return allocations.isEmpty() ? null : allocations.get(0);
    }

    @Override
    public double sumVenueIncomeByVenueId(Integer venueId) {
        Double res = (Double) hibernateTemplate
                .find("select sum(a.venueIncome) from Allocation a where a.venueId=?", venueId)
                .iterator()
                .next();
        return res == null ? 0 : res;
    }
}
