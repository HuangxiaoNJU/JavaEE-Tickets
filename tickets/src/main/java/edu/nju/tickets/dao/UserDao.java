package edu.nju.tickets.dao;

import edu.nju.tickets.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User, Integer> {

    User findByEmailAndPassword(final String email, final String password);

    User findByEmail(final String email);

    List<Object[]> findLevelAndUserNumber();

    Long sumPoints();

}
