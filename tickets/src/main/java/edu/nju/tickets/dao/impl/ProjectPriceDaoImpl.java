package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.ProjectPriceDao;
import edu.nju.tickets.entity.ProjectPrice;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProjectPriceDaoImpl extends BaseDaoImpl<ProjectPrice, Integer> implements ProjectPriceDao {

    public List<ProjectPrice> getByProjectId(Integer projectId) {
        return find("from ProjectPrice p where p.project_id=? order by p.project_id desc", projectId);
    }
}
