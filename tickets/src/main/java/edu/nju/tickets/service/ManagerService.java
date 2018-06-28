package edu.nju.tickets.service;

import edu.nju.tickets.vo.PlatformStatisticsVO;

public interface ManagerService {

    /**
     * 经理登录
     *
     * @param managerName   经理名
     * @param password      密码
     * @return              是否成功
     */
    boolean login(final String managerName, final String password);

    /**
     * 获取平台统计信息
     *
     * @param managerName   经理名
     * @return              平台统计信息vo
     */
    PlatformStatisticsVO getPlatformStatistics(final String managerName);

}
