package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Venue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class VenueDaoTest {

    @Autowired
    private VenueDao venueDao;

    @Test
    public void testSave() {
//        Venue venue = new Venue();
//        venue.setIdentification("1234567");
//        venue.setLocation("南京鼓楼区");
//        venue.setSeatNumber(500);
//
//        venueDao.save(venue);
    }

//    @Test
//    public void testSearch() {
//        List<Venue> venues = venueDao.findByNameLike("%南%");
//        System.out.println(venues.size());
//        venues.forEach(v -> System.out.println(v.getName()));
//    }
//
//    @Test
//    public void testFind() {
//        Venue venue = venueDao.get(1);
//        System.out.println(venue.isChecked());
//    }
//
//    @Test
//    public void testFindUnChecked() {
//        List<Venue> venues = venueDao.findByCheckedOrderByRegisterTime(false);
//        System.out.println(venues.size());
//    }

}
