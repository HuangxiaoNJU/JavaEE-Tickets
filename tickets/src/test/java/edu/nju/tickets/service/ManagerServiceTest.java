package edu.nju.tickets.service;

import edu.nju.tickets.vo.PlatformStatisticsVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class ManagerServiceTest {

    @Autowired
    private ManagerService managerService;

    @Test
    public void testGetPlatformStatistics() {
        PlatformStatisticsVO vo = managerService.getPlatformStatistics("huangxiao");

        vo.getProfitPerDay().forEach((k, v) -> System.out.println(k + ": " + v));
        vo.getProfitPerMonth().forEach((k, v) -> System.out.println(k + ": " + v));
        vo.getProfitPerYear().forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
