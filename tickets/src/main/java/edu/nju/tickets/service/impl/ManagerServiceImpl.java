package edu.nju.tickets.service.impl;

import edu.nju.tickets.dao.AllocationDao;
import edu.nju.tickets.dao.ManagerDao;
import edu.nju.tickets.dao.OrderFormDao;
import edu.nju.tickets.dao.UserDao;
import edu.nju.tickets.entity.Allocation;
import edu.nju.tickets.entity.Manager;
import edu.nju.tickets.service.ManagerService;
import edu.nju.tickets.util.DateTimeUtil;
import edu.nju.tickets.vo.PlatformStatisticsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static edu.nju.tickets.util.Constants.NUMBER_FORMAT;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerDao managerDao;
    @Resource
    private AllocationDao allocationDao;
    @Resource
    private OrderFormDao orderFormDao;
    @Resource
    private UserDao userDao;

    @Override
    public boolean login(String managerName, String password) {
        Manager manager = managerDao.findByManagerNameAndPassword(managerName, password);
        return manager != null;
    }

    /**
     * 获取平台收益按年、月、日分别统计的信息
     *
     * @param allocations   所有分配记录（已按时间排好序）
     * @param endIndex      "yyyy-MM-dd"中结束字符后一个位置下标，按年统计为4，按月统计为7，按日统计为10
     * @return              Map
     */
    private Map<String, Double> getProfitMap(List<Allocation> allocations, int endIndex) {
        return allocations.stream()
                .collect(
                        Collectors.groupingBy(
                                a -> DateTimeUtil.convertTimestampToString(a.getTime()).substring(0, endIndex),
                                TreeMap::new,   // 使Map中日期有序
                                Collectors.summingDouble(Allocation::getPlatformIncome)
                        )
                );
    }

    private Map<String, Map<String, Double>> listToNestMap(List<Object[]> list, int dateNum) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        objects -> {
                            StringBuilder sb = new StringBuilder();
                            sb.append(objects[2]);
                            for (int i = 3; i < 2 + dateNum; i++) {
                                sb.append("-").append(objects[i]);
                            }
                            return sb.toString();
                        },
                        TreeMap::new,
                        Collectors.groupingBy(objects -> String.valueOf(objects[0]), Collectors.summingDouble(objects -> (Double) objects[1]))
                    )
                );
    }

    @Override
    public PlatformStatisticsVO getPlatformStatistics(String managerName) {
        Manager manager = managerDao.findByManagerName(managerName);
        if (manager == null) {
            throw new RuntimeException("管理员不存在");
        }
        PlatformStatisticsVO vo = new PlatformStatisticsVO();

        List<Allocation> allocations = allocationDao.findAll();
        vo.setProfitPerYear(getProfitMap(allocations, 4));
        vo.setProfitPerMonth(getProfitMap(allocations, 7));
        vo.setProfitPerDay(getProfitMap(allocations, 10));

        double totalPoints = orderFormDao.sumTotalPrice();
        long remainPoints = userDao.sumPoints();
        vo.setExchangeRatio(NUMBER_FORMAT.format((totalPoints - remainPoints) / totalPoints));

        vo.setTypeProfitPerDay(listToNestMap(allocationDao.sumPlatformIncomeGroupByProjectTypeAndDay(), 3));
        vo.setTypeProfitPerMonth(listToNestMap(allocationDao.sumPlatformIncomeGroupByProjectTypeAndMonth(), 2));
        vo.setTypeProfitPerYear(listToNestMap(allocationDao.sumPlatformIncomeGroupByProjectTypeAndYear(), 1));

        return vo;
    }

}
