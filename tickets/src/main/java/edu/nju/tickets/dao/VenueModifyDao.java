package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Venue;
import edu.nju.tickets.entity.VenueModify;

import java.util.List;

public interface VenueModifyDao extends BaseDao<VenueModify, Integer> {

    List<VenueModify> findByIsCheckedOrderByApplyTime(final boolean isChecked);

    VenueModify findByVenueAndIsChecked(final Venue venue, final boolean isChecked);

}
