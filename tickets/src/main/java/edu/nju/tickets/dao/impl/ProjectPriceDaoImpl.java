package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.ProjectPriceDao;
import edu.nju.tickets.entity.ProjectPrice;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProjectPriceDaoImpl extends BaseDaoImpl<ProjectPrice, Integer> implements ProjectPriceDao {

    public List<ProjectPrice> getByProjectId(Integer projectId) {
        return find("from ProjectPrice p where p.projectId=? order by p.projectId desc", projectId);
    }

    @Override
    public List<Object[]> sumSeatNumberGroupByVenue() {
        Query query = hibernateTemplate.getSessionFactory().getCurrentSession()
                .createQuery("select p.project.venue, sum(p.seatNumber) from ProjectPrice p group by p.project.venue");

        return query.list();
    }

}
