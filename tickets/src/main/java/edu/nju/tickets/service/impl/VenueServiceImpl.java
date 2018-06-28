package edu.nju.tickets.service.impl;

import edu.nju.tickets.dao.*;
import edu.nju.tickets.entity.*;
import edu.nju.tickets.service.MailService;
import edu.nju.tickets.service.VenueInfo;
import edu.nju.tickets.service.VenueService;
import edu.nju.tickets.util.DateTimeUtil;
import edu.nju.tickets.util.RandomUtil;
import edu.nju.tickets.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static edu.nju.tickets.util.Constants.VENUE_IDENTIFICATION_LENGTH;

@Service
public class VenueServiceImpl implements VenueService, VenueInfo {

    @Resource
    private VenueDao venueDao;
    @Resource
    private VenueModifyDao venueModifyDao;
    @Resource
    private PayDao payDao;
    @Resource
    private ProjectDao projectDao;
    @Resource
    private ProjectPriceDao projectPriceDao;
    @Resource
    private AllocationDao allocationDao;
    @Resource
    private OrderFormDao orderFormDao;
    @Resource
    private MailService mailService;

    private VenueInfoVO convertVenueToVO(Venue venue) {
        if (venue == null) {
            return null;
        }
        VenueInfoVO vo = new VenueInfoVO();
        vo.setId(venue.getId());
        vo.setEmail(venue.getEmail());
        vo.setName(venue.getName());
        vo.setLocation(venue.getLocation());
        vo.setSeatNumber(venue.getSeatNumber());
        vo.setRegisterTime(DateTimeUtil.convertTimestampToString(venue.getRegisterTime()));

        return vo;
    }

    private VenueModifyInfoVO convertVenueModifyToVO(VenueModify venueModify) {
        if (venueModify == null) {
            return null;
        }

        VenueModifyInfoVO vo = new VenueModifyInfoVO();

        vo.setId(venueModify.getId());
        vo.setVenueInfoVO(convertVenueToVO(venueModify.getVenue()));
        vo.setApplyTime(DateTimeUtil.convertTimestampToString(venueModify.getApplyTime()));
        vo.setChecked(venueModify.isChecked());
        vo.setPass(venueModify.isPass());
        vo.setNewName(venueModify.getNewName());
        vo.setNewSeatNumber(venueModify.getNewSeatNumber());
        vo.setNewLocation(venueModify.getNewLocation());

        return vo;
    }

    @Override
    public List<VenueInfoVO> searchVenue(String keywords) {
        return venueDao
                .findByNameLike("%" + keywords + "%")
                .stream()
                .map(this::convertVenueToVO)
                .collect(Collectors.toList());
    }

    @Override
    public void register(VenueRegisterVO vo) {
        if (venueDao.findByEmail(vo.getEmail()) != null) {
            throw new RuntimeException("邮箱已被注册");
        }

        if (!mailService.verifyCode(vo.getEmail(), vo.getVerificationCode())) {
            throw new RuntimeException("验证码错误");
        }

        Venue venue = new Venue();
        venue.setName(vo.getName());
        venue.setLocation(vo.getLocation());
        venue.setSeatNumber(vo.getSeatNumber());
        venue.setEmail(vo.getEmail());
        venue.setRegisterTime(DateTimeUtil.getCurrentTimestamp());

        venue.setChecked(false);
        venue.setPass(false);

        venueDao.save(venue);
    }

    @Override
    public boolean login(String email, String identification) {
        Venue venue = venueDao.findByEmailAndIdentification(email, identification);
        return venue != null && venue.isPass();
    }

    /**
     * 随机生成场馆识别码
     *
     * @return 识别码
     */
    private String generateIdentification() {
        String identification;
        do {
            identification = RandomUtil.getRandomString(VENUE_IDENTIFICATION_LENGTH);
        } while (venueDao.findByIdentification(identification) != null);

        return identification;
    }

    @Override
    public void checkVenueRegister(Integer venueId, boolean isPass) {
        Venue venue = venueDao.get(venueId);
        if (venue == null) {
            throw new RuntimeException("场馆不存在");
        }
        if (venue.isChecked()) {
            throw new RuntimeException("场馆已被审核，结果为" + (venue.isPass() ? "通过" : "未通过"));
        }

        if (isPass) {
            venue.setChecked(true);
            venue.setPass(true);

            String identification = generateIdentification();
            venue.setIdentification(identification);
            venueDao.update(venue);

            // TODO 发送邮件通知场馆识别码
//            mailService.sendMessage(
//                    venue.getEmail(),
//                    "Tickets场馆注册审核通过",
//                    "您的识别码是" + identification
//            );
        } else {
            venue.setChecked(true);
            venue.setPass(false);
            venueDao.update(venue);
            // TODO 速度太慢
//            mailService.sendMessage(
//                    venue.getEmail(),
//                    "Tickets场馆注册审核未通过",
//                    "您申请的场馆\"" + venue.getName() + "\"未通过审核"
//            );
        }
    }

    @Override
    public void checkVenueModify(Integer modifyId, boolean isPass) {
        VenueModify venueModify = venueModifyDao.get(modifyId);
        if (venueModify == null) {
            throw new RuntimeException("修改记录不存在");
        }
        // 未通过审核
        if (!isPass) {
            venueModify.setChecked(true);
            venueModify.setPass(false);
            venueModifyDao.update(venueModify);
            return;
        }

        Venue venue = venueModify.getVenue();
        if (venueModify.getNewName() != null) {
            venue.setName(venueModify.getNewName());
        }
        if (venueModify.getNewSeatNumber() != null) {
            venue.setSeatNumber(venueModify.getNewSeatNumber());
        }
        if (venueModify.getNewLocation() != null) {
            venue.setLocation(venueModify.getNewLocation());
        }
        venueDao.update(venue);

        venueModify.setChecked(true);
        venueModify.setPass(true);
        venueModifyDao.update(venueModify);
    }

    @Override
    public VenueInfoVO getVenueInfoById(Integer id) {
        return convertVenueToVO(venueDao.get(id));
    }

    @Override
    public VenueInfoVO getVenueInfoByIdentification(String identification) {
        Venue venue = venueDao.findByIdentification(identification);
        return convertVenueToVO(venue);
    }

    @Override
    public List<VenueInfoVO> getVenues(String property, String order) {
        List<Venue> venues = venueDao.findAll(property, order);
        return venues
                .stream()
                .filter(Venue::isPass)  // 过滤出已审核通过场馆
                .map(this::convertVenueToVO)
                .collect(Collectors.toList());
    }

    @Override
    public void applyForChangeInfo(String identification, VenueChangeVO vo) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            throw new RuntimeException("场馆不存在");
        }
        if (!venue.isPass()) {
            throw new RuntimeException("场馆未通过审核");
        }

        boolean isModified = false;
        VenueModify venueModify = new VenueModify();
        if (!venue.getName().equals(vo.getNewName())) {
            venueModify.setNewName(vo.getNewName());
            isModified = true;
        }
        if (venue.getSeatNumber() != vo.getNewSeatNumber()) {
            venueModify.setNewSeatNumber(vo.getNewSeatNumber());
            isModified = true;
        }
        if (!venue.getLocation().equals(vo.getNewLocation())) {
            venueModify.setNewLocation(vo.getNewLocation());
            isModified = true;
        }

        if (!isModified) {
            throw new RuntimeException("场馆信息未做修改");
        }

        // 覆盖（删除）上一次未审核修改
        VenueModify lastUncheckedModify = venueModifyDao.findByVenueAndIsChecked(venue, false);
        if (lastUncheckedModify != null) {
            venueModifyDao.delete(lastUncheckedModify);
        }

        venueModify.setVenue(venue);
        venueModify.setApplyTime(DateTimeUtil.getCurrentTimestamp());
        venueModify.setChecked(false);
        venueModify.setPass(false);

        venueModifyDao.save(venueModify);
    }

    @Override
    public List<VenueInfoVO> getVenuesByIsChecked(boolean isChecked) {
        return venueDao
                .findByCheckedOrderByRegisterTime(isChecked)
                .stream()
                .map(this::convertVenueToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VenueModifyInfoVO> getVenueModifyInfo(boolean isChecked) {
        return venueModifyDao
                .findByIsCheckedOrderByApplyTime(isChecked)
                .stream()
                .map(this::convertVenueModifyToVO)
                .collect(Collectors.toList());
    }

    @Override
    public VenueStatisticsVO getVenueStatistics(String identification) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            return null;
        }
        VenueStatisticsVO vo = new VenueStatisticsVO();
        vo.setProfit(allocationDao.sumVenueIncomeByVenueId(venue.getId()));
        vo.setIncome(payDao.sumMoneyByVenueId(venue.getId()));

        vo.setOnlineIncome(payDao.sumMoneyByVenueIdAndIsOnline(venue.getId(), true));
        vo.setOfflineIncome(payDao.sumMoneyByVenueIdAndIsOnline(venue.getId(), false));

        List<Object[]> projectsIncome = payDao.findProjectIncomeByVenueId(venue.getId());
        vo.setProjectIncomeList(
                projectsIncome
                        .stream()
                        .map(e -> (Double) e[0])
                        .collect(Collectors.toList()));
        vo.setProjectNameList(
                projectsIncome
                        .stream()
                        .map(e -> projectDao.get((Integer) e[1]).getName())
                        .collect(Collectors.toList()));

        return vo;
    }

    @Override
    public VenuesStatisticsVO getVenuesStatistics() {
        VenuesStatisticsVO vo = new VenuesStatisticsVO();
        List<Venue> venues = venueDao.findAll("id", "asc");
        vo.setVenueList(venues.stream().map(Venue::getName).collect(Collectors.toList()));
        vo.setIncomeList(venues.stream().map(v -> payDao.sumMoneyByVenueId(v.getId())).collect(Collectors.toList()));
        return vo;
    }

    @Override
    public Double getAverageScore(Integer venueId) {
        double score = 0.0;
        int sum = 0;
        List<Project> projects = projectDao.findByVenueId(venueId);
        for (int i = 0; i < projects.size(); i++) {
            List<ProjectPrice> prices = projectPriceDao.getByProjectId(projects.get(i).getId());
            for (int j = 0; j < prices.size(); j++) {
                List<OrderForm> orderForms = orderFormDao.findByProjectPriceId(prices.get(j).getId());
                for (int k = 0; k < orderForms.size(); k++) {
                    if (orderForms.get(k).getScore() != -1) {
                        sum++;
                        score += orderForms.get(k).getScore();
                    }
                }

            }
        }
        return score / sum;
    }

    /**
     * 获取场馆收益按年、月、日分别统计的信息
     *
     * @param allocations   分配结算列表
     * @param endIndex      "yyyy-MM-dd"中结束字符后一个位置下标，按年统计为4，按月统计为7，按日统计为10
     * @return              Map
     */
    private Map<String, Double> getProfitMap(List<Allocation> allocations, int endIndex) {
        return allocations.stream()
                .collect(
                        Collectors.groupingBy(
                                a -> DateTimeUtil.convertTimestampToString(a.getTime()).substring(0, endIndex),
                                TreeMap::new,
                                Collectors.summingDouble(Allocation::getVenueIncome)
                        )
                );
    }

    @Override
    public VenueIndividualStatistics getVenueIndividualStatistics(String identification) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            throw new RuntimeException("场馆不存在");
        }

        VenueIndividualStatistics vo = new VenueIndividualStatistics();

        vo.setTotalProjectNum(projectDao.findByVenue(venue).size());

        List<Allocation> allocations = allocationDao.findByVenueId(venue.getId());
        vo.setProfitPerDay(getProfitMap(allocations, 10));
        vo.setProfitPerMonth(getProfitMap(allocations, 7));
        vo.setProfitPerYear(getProfitMap(allocations, 4));

        vo.setTypeProfitPerDay(allocationDao.listToNestMap(allocationDao.sumVenueIncomeByVenueGroupByTypeAndDay(venue), 3));
        vo.setTypeProfitPerMonth(allocationDao.listToNestMap(allocationDao.sumVenueIncomeByVenueGroupByTypeAndMonth(venue), 2));
        vo.setTypeProfitPerYear(allocationDao.listToNestMap(allocationDao.sumVenueIncomeByVenueGroupByTypeAndYear(venue), 1));

        vo.setPresentRatio("19%");
        vo.setRefundRatio("7%");

        return vo;
    }

}
