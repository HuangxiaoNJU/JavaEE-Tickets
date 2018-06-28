package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Manager;

public interface ManagerDao extends BaseDao<Manager, Integer> {

    Manager findByManagerNameAndPassword(final String managerName, final String password);

    Manager findByManagerName(final String managerName);

}
