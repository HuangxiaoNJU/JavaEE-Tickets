package edu.nju.tickets.service.impl;

import edu.nju.tickets.dao.ManagerDao;
import edu.nju.tickets.entity.Manager;
import edu.nju.tickets.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerDao managerDao;

    @Override
    public boolean login(String managerName, String password) {
        Manager manager = managerDao.findByManagerNameAndPassword(managerName, password);
        return manager != null;
    }

}
