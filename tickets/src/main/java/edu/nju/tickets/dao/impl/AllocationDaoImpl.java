package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.AllocationDao;
import edu.nju.tickets.entity.Allocation;
import edu.nju.tickets.entity.Venue;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public class AllocationDaoImpl extends BaseDaoImpl<Allocation, Integer> implements AllocationDao {

    @Override
    public Allocation findByProjectId(Integer projectId) {
        List<Allocation> allocations = find("from Allocation a where a.projectId=?", projectId);
        return allocations.isEmpty() ? null : allocations.get(0);
    }

    @Override
    public List<Allocation> findByVenueId(Integer venueId) {
        return find("from Allocation a where a.venueId=?", venueId);
    }

    @Override
    public double sumVenueIncomeByVenueId(Integer venueId) {
        Double res = (Double) hibernateTemplate
                .find("select sum(a.venueIncome) from Allocation a where a.venueId=?", venueId)
                .iterator()
                .next();
        return res == null ? 0 : res;
    }

    @Override
    public List<Object[]> sumPlatformIncomeGroupByProjectTypeAndDay() {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select p.type, sum(a.platformIncome), YEAR(a.time), MONTH(a.time), DAY(a.time)" +
                        "from Allocation a, Project p " +
                        "where a.projectId = p.id " +
                        "group by p.type, YEAR(a.time), MONTH(a.time), DAY(a.time)" +
                        "order by YEAR(a.time), MONTH(a.time), DAY(a.time)");

        return query.list();
    }

    @Override
    public List<Object[]> sumPlatformIncomeGroupByProjectTypeAndMonth() {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select p.type, sum(a.platformIncome), YEAR(a.time), MONTH(a.time)" +
                        "from Allocation a, Project p " +
                        "where a.projectId = p.id " +
                        "group by p.type, YEAR(a.time), MONTH(a.time)" +
                        "order by YEAR(a.time), MONTH(a.time)");

        return query.list();
    }

    @Override
    public List<Object[]> sumPlatformIncomeGroupByProjectTypeAndYear() {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select p.type, sum(a.platformIncome), YEAR(a.time) " +
                        "from Allocation a, Project p " +
                        "where a.projectId = p.id " +
                        "group by p.type, YEAR(a.time) " +
                        "order by YEAR(a.time)");

        return query.list();
    }

    @Override
    public List<Object[]> sumVenueIncomeByVenueGroupByTypeAndYear(Venue venue) {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select p.type, sum(a.venueIncome), YEAR(a.time)" +
                        "from Allocation a, Project p " +
                        "where a.projectId = p.id and p.venue=?1 " +
                        "group by p.type, YEAR(a.time) " +
                        "order by YEAR(a.time)");

        query.setParameter(1, venue);
        return query.list();
    }

    @Override
    public List<Object[]> sumVenueIncomeByVenueGroupByTypeAndMonth(Venue venue) {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select p.type, sum(a.venueIncome), YEAR(a.time), MONTH(a.time)" +
                        "from Allocation a, Project p " +
                        "where a.projectId = p.id and p.venue=?1 " +
                        "group by p.type, YEAR(a.time), MONTH(a.time) " +
                        "order by YEAR(a.time), MONTH(a.time)");

        query.setParameter(1, venue);
        return query.list();
    }

    @Override
    public List<Object[]> sumVenueIncomeByVenueGroupByTypeAndDay(Venue venue) {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select p.type, sum(a.venueIncome), YEAR(a.time), MONTH(a.time), DAY(a.time)" +
                        "from Allocation a, Project p " +
                        "where a.projectId = p.id and p.venue=?1 " +
                        "group by p.type, YEAR(a.time), MONTH(a.time), DAY(a.time) " +
                        "order by YEAR(a.time), MONTH(a.time), DAY(a.time)");

        query.setParameter(1, venue);
        return query.list();
    }
}
