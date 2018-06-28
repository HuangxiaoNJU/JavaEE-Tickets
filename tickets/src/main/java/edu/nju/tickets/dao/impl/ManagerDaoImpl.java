package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.ManagerDao;
import edu.nju.tickets.entity.Manager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManagerDaoImpl extends BaseDaoImpl<Manager, Integer> implements ManagerDao {

    @Override
    public Manager findByManagerNameAndPassword(String managerName, String password) {
        List<Manager> managers = find("from Manager m where m.managerName=? and m.password=?", managerName, password);
        return managers.isEmpty() ? null : managers.get(0);
    }

}
