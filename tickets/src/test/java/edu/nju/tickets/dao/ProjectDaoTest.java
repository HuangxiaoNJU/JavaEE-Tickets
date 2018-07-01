package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Project;
import edu.nju.tickets.entity.ProjectPrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class ProjectDaoTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private VenueDao venueDao;

    @Test
    public void testSave() {
//        Project project = new Project();
//        project.setName("开学典礼");
//        project.setBeginTime(Timestamp.valueOf(LocalDateTime.now()));
//        project.setEndTime(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
//        project.setType("演讲");
//        project.setDescription("吕建演讲");
//        project.setVenue(venueDao.get(1));
//
//        ProjectPrice price1 = new ProjectPrice();
//        price1.setSeatName("VIP座位");
//        price1.setPrice(100);
//        price1.setSeatNumber(500);
//        price1.setProject(project);
//        ProjectPrice price2 = new ProjectPrice();
//        price2.setSeatName("普通座位");
//        price2.setPrice(50);
//        price2.setSeatNumber(500);
//        price2.setProject(project);
//
//        List<ProjectPrice> priceList = new ArrayList<>();
//        priceList.add(price1);
//        priceList.add(price2);
//
//        project.setPriceList(priceList);
//
//        projectDao.save(project);
    }

//    @Test
//    public void testFind() {
//        Project project = projectDao.get(11);
//        List<ProjectPrice> list = project.getPriceList();
//        list.forEach(p -> System.out.println(p.getSeatName() + "\t" + p.getPrice()));
//    }
//
//    @Test
//    public void testFindAll() {
//        List<Project> projects = projectDao.findAll("id", "desc");
//        projects.forEach(p -> System.out.println(p.getName()));
//    }

    @Test
    public void addPoster() throws Exception {
        for (int i = 18; i < 55; i++) {
            Project project = projectDao.get(i);
            project.setPosterURL("/poster/pic/" + i + ".jpg");
            projectDao.update(project);
        }
    }

}
