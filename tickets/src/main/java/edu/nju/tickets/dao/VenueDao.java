package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Venue;

import java.util.List;

public interface VenueDao extends BaseDao<Venue, Integer> {

    List<Venue> findAll(final String property, final String order);

    List<Venue> findByNameLike(String keywords);

    Venue findByEmail(final String email);

    Venue findByEmailAndIdentification(final String email, final String identification);

    Venue findByIdentification(final String identification);

    List<Venue> findByCheckedOrderByRegisterTime(final boolean isChecked);

}
