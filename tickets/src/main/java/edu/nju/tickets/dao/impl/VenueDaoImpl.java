package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.VenueDao;
import edu.nju.tickets.entity.Venue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VenueDaoImpl extends BaseDaoImpl<Venue, Integer> implements VenueDao {

    @Override
    public List<Venue> findAll(String property, String order) {
        return find("from Venue v order by v." + property + " " + order);
    }

    @Override
    public List<Venue> findByNameLike(String keywords) {
        return find("from Venue v where v.name like ?", keywords);
    }

    @Override
    public Venue findByEmail(String email) {
        List<Venue> venues = find("from Venue v where v.email=?", email);
        return venues.isEmpty() ? null : venues.get(0);
    }

    @Override
    public Venue findByEmailAndIdentification(String email, String identification) {
        List<Venue> venues = find("from Venue v where v.email=? and v.identification=?", email, identification);
        return venues.isEmpty() ? null : venues.get(0);
    }

    @Override
    public Venue findByIdentification(String identification) {
        List<Venue> venues = find("from Venue v where v.identification=?", identification);
        return venues.isEmpty() ? null : venues.get(0);
    }

    @Override
    public List<Venue> findByCheckedOrderByRegisterTime(boolean isChecked) {
        return find("from Venue v where v.checked=? order by v.registerTime", isChecked);
    }

}
