package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.UserDao;
import edu.nju.tickets.entity.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

    @Override
    public User findByEmailAndPassword(String email, String password) {
        List<User> users = find("from User u where u.email=? and password=?", email, password);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = find("from User u where u.email=?", email);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<Object[]> findLevelAndUserNumber() {
        return (List<Object[]>) hibernateTemplate.find("select count(*), u.level from User u group by u.level");
    }

    @Override
    public Long sumPoints() {
        Query query = hibernateTemplate.getSessionFactory()
                .getCurrentSession()
                .createQuery("select sum(u.points) from User u");

        return query.uniqueResult() == null ? 0L : (Long) query.uniqueResult();
    }

}
