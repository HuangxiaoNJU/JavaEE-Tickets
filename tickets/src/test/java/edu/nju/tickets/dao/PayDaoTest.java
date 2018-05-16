package edu.nju.tickets.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class PayDaoTest {

    @Autowired
    private PayDao payDao;

    @Test
    public void testSumMoneyByProjectIdAndIsOnline() {
//        System.out.println(payDao.sumMoneyByProjectIdAndIsOnline(1, false));
    }

    @Test
    public void testFindProjectIncomeByVenueId() {
//        System.out.println(payDao.findProjectIncomeByVenueId(2).get(0)[0]);
    }

}
