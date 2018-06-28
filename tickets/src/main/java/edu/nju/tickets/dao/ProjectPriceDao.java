package edu.nju.tickets.dao;

import edu.nju.tickets.entity.ProjectPrice;
import java.util.List;

public interface ProjectPriceDao extends BaseDao<ProjectPrice, Integer> {

    List<ProjectPrice> getByProjectId(Integer projectId);
}
