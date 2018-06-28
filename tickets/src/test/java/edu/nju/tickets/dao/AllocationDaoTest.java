package edu.nju.tickets.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class AllocationDaoTest {

    @Autowired
    private AllocationDao allocationDao;

    @Test
    public void testSumPlatformIncomeGroupByProjectTypeAndTime() {
        List<Object[]> res = allocationDao.sumPlatformIncomeGroupByProjectTypeAndMonth();
        res.forEach(objects -> {
            for (Object object : objects) {
                System.out.println(object);
            }
        });
    }

}
