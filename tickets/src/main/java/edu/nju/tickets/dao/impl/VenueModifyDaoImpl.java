package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.VenueModifyDao;
import edu.nju.tickets.entity.Venue;
import edu.nju.tickets.entity.VenueModify;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VenueModifyDaoImpl extends BaseDaoImpl<VenueModify, Integer> implements VenueModifyDao {

    @Override
    public List<VenueModify> findByIsCheckedOrderByApplyTime(boolean isChecked) {
        return find("from VenueModify v where v.checked=? order by v.applyTime", isChecked);
    }

    @Override
    public VenueModify findByVenueAndIsChecked(Venue venue, boolean isChecked) {
        List<VenueModify> res = find("from VenueModify v where v.venue=? and v.checked=?", venue, isChecked);
        return res.isEmpty() ? null : res.get(0);
    }
}
