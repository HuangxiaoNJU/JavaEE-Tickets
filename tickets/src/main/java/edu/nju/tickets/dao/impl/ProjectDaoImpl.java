package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.ProjectDao;
import edu.nju.tickets.entity.Project;
import edu.nju.tickets.entity.Venue;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl<Project, Integer> implements ProjectDao {

    @Override
    public List<Project> findByNameLike(String keywords) {
        return find("from Project p where p.name like ? order by p.id desc", keywords);
    }

    @Override
    public List<Project> findAll(String property, String order) {
        return find("from Project p order by p." + property + " " + order);
    }

    @Override
    public List<Project> findByVenue(Venue venue) {
        return find("from Project p where p.venue = ?", venue);
    }

    @Override
    public List<Project> findByVenueAndBeginTimeLargerThanOrderByBeginTime(Venue venue, Timestamp timestamp) {
        return find("from Project p where p.venue=? and p.beginTime > ? order by p.beginTime", venue, timestamp);
    }

    @Override
    public List<Project> findByVenueAndBeginTimeLessThanAndEndTimeLargerThan(Venue venue, Timestamp timestamp1, Timestamp timestamp2) {
        return find("from Project p where p.venue=? and p.beginTime < ? and p.endTime > ?", venue, timestamp1, timestamp2);
    }

    @Override
    public List<Project> findByVenueAndEndTimeLessThanOrderByEndTimeDesc(Venue venue, Timestamp timestamp) {
        return find("from Project p where p.venue=? and p.endTime < ? order by p.endTime desc", venue, timestamp);
    }

    @Override
    public List<Project> findByEndTimeLessThanOrderByEndTime(Timestamp timestamp) {
        return find("from Project p where p.endTime < ? order by p.endTime", timestamp);
    }

}
