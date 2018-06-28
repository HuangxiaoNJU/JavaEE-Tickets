package edu.nju.tickets.service;

import edu.nju.tickets.vo.PlatformStatisticsVO;

public interface ManagerService {

    boolean login(final String managerName, final String password);

    PlatformStatisticsVO getPlatformStatistics(final String managerName);

}
