package edu.nju.tickets.dao;

import edu.nju.tickets.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml"})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSave() {
//        User user = new User();
//        user.setEmail("151250051@smail.nju.edu.cn");
//        user.setNickname("XianXian");
//        user.setPassword("123456");
//        user.setLevel(1);
//        user.setPoints(0);
//
//        userDao.save(user);
    }

    @Test
    public void testFindByEmailAndPassword() {
//        User user = userDao.findByEmailAndPassword("hx36w35@163.com", "123456");
//        System.out.println(user.getNickname());
    }

}
