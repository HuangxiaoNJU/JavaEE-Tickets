package edu.nju.tickets.service;

import edu.nju.tickets.vo.IndividualStatisticsVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetIndividualStatistics() {
        IndividualStatisticsVO vo = userService.getIndividualStatistics("151250051@smail.nju.edu.cn");
        vo.getConsumePerMonth().forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
