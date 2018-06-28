package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Project;
import edu.nju.tickets.entity.Venue;

import java.sql.Timestamp;
import java.util.List;

public interface ProjectDao extends BaseDao<Project, Integer> {

    List<Project> findByNameLike(String keywords);

    List<Project> findAll(String property, String order);

    List<Project> findByType(String type);

    List<Project> findByVenue(Venue venue);

    List<Project> findByVenueAndBeginTimeLargerThanOrderByBeginTime(Venue venue, Timestamp timestamp);

    List<Project> findByVenueAndBeginTimeLessThanAndEndTimeLargerThan(Venue venue, Timestamp timestamp1, Timestamp timestamp2);

    List<Project> findByVenueAndEndTimeLessThanOrderByEndTimeDesc(Venue venue, Timestamp timestamp);

    List<Project> findByEndTimeLessThanOrderByEndTime(Timestamp timestamp);

    List<Project> findByVenueId(Integer venueId);

}
