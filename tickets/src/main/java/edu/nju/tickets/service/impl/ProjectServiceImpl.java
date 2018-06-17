package edu.nju.tickets.service.impl;

import edu.nju.tickets.dao.*;
import edu.nju.tickets.entity.*;
import edu.nju.tickets.service.ProjectInfo;
import edu.nju.tickets.service.ProjectService;
import edu.nju.tickets.service.VenueInfo;
import edu.nju.tickets.util.Constants;
import edu.nju.tickets.util.DateTimeUtil;
import edu.nju.tickets.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static edu.nju.tickets.util.Constants.ProjectState.*;

@Service
public class ProjectServiceImpl implements ProjectService, ProjectInfo {

    @Resource
    private ProjectDao projectDao;

    @Resource
    private VenueDao venueDao;
    @Resource
    private VenueInfo venueInfo;
    @Resource
    private AllocationDao allocationDao;
    @Resource
    private PayDao payDao;
    @Resource
    private OrderFormDao orderFormDao;

    @Override
    public PriceInfoVO convertProjectPriceToVO(ProjectPrice projectPrice) {
        PriceInfoVO vo = new PriceInfoVO();
        vo.setId(projectPrice.getId());
        vo.setPrice(projectPrice.getPrice());
        vo.setSeatName(projectPrice.getSeatName());
        vo.setSeatNumber(projectPrice.getSeatNumber());
        vo.setRow(projectPrice.getRow());
        vo.setColumn(projectPrice.getColumn());

        List<String> unavailableList = new ArrayList<>();
        orderFormDao
                .findByProjectPrice(projectPrice)
                .stream()
                .filter(o -> o.getState() == Constants.OrderFormState.FINISHED
                          || o.getState() == Constants.OrderFormState.NOT_PAYED)
                .filter(o -> o.getSeatList() != null)   // 未支付订单可能尚未分配座位
                .map(OrderForm::getSeatList)
                .map(s -> Arrays.asList(s.split(",")))
                .forEach(unavailableList::addAll);
        vo.setUnavailableList(unavailableList);

        return vo;
    }

    @Override
    public ProjectInfoVO convertProjectToVO(Project project) {
        if (project == null) {
            return null;
        }
        ProjectInfoVO vo = new ProjectInfoVO();

        vo.setId(project.getId());
        vo.setName(project.getName());
        vo.setDescription(project.getDescription());
        vo.setType(project.getType());
        vo.setBeginTime(DateTimeUtil.convertTimestampToString(project.getBeginTime()));
        vo.setEndTime(DateTimeUtil.convertTimestampToString(project.getEndTime()));
        vo.setPosterURL(project.getPosterURL());

        vo.setVenueInfoVO(venueInfo.getVenueInfoById(project.getVenue().getId()));

        int totalPeople = project.getPriceList().stream().map(ProjectPrice::getSeatNumber).reduce(0, (a, b) -> a + b);
        vo.setTotalPeople(totalPeople);

        List<PriceInfoVO> prices = project.getPriceList().stream().map(this::convertProjectPriceToVO).collect(Collectors.toList());
        vo.setPrices(prices);
        return vo;
    }

    private ProjectIncomeVO convertProjectToIncomeVO(Project project) {
        if (project == null) {
            return null;
        }
        ProjectIncomeVO vo = new ProjectIncomeVO();
        vo.setProjectInfoVO(convertProjectToVO(project));

        vo.setOnlineIncome(payDao.sumMoneyByProjectIdAndIsOnline(project.getId(), true));
        vo.setOfflineIncome(payDao.sumMoneyByProjectIdAndIsOnline(project.getId(), false));

        Allocation allocation = allocationDao.findByProjectId(project.getId());
        // 未分配
        if (allocation == null) {
            vo.setAllocated(false);
            return vo;
        }
        vo.setAllocated(true);
        vo.setRatio(allocation.getRatio());
        vo.setPlatformIncome(allocation.getPlatformIncome());
        vo.setVenueIncome(allocation.getVenueIncome());

        return vo;
    }

    @Override
    public List<ProjectInfoVO> searchProject(String keywords) {
        return projectDao
                .findByNameLike("%" + keywords + "%")
                .stream()
                .map(this::convertProjectToVO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectInfoVO getProjectInfo(Integer id) {
        return convertProjectToVO(projectDao.get(id));
    }

    @Override
    public List<ProjectInfoVO> getProjects(String property, String order) {
        return projectDao
                .findAll(property, order)
                .stream()
                .map(this::convertProjectToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectInfoVO> getProjectsByVenue(Integer venueId, String state) {
        Venue venue = venueDao.get(venueId);
        // venue不存在
        if (venue == null) {
            return null;
        }

        Timestamp current = DateTimeUtil.getCurrentTimestamp();
        List<Project> projects;
        switch (state) {
            case NOT_BEGIN:
                projects = projectDao.findByVenueAndBeginTimeLargerThanOrderByBeginTime(venue, current);
                break;
            case UNDERWAY:
                projects = projectDao.findByVenueAndBeginTimeLessThanAndEndTimeLargerThan(venue, current, current);
                break;
            case FINISHED:
                projects = projectDao.findByVenueAndEndTimeLessThanOrderByEndTimeDesc(venue, current);
                break;
            default:
                projects = projectDao.findByVenue(venue);
                break;
        }

        return projects
                .stream()
                .map(this::convertProjectToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectInfoVO> getProjectsByVenue(String identification, String state) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            return null;
        }
        return getProjectsByVenue(venue.getId(), state);
    }

    @Override
    public List<ProjectIncomeVO> getProjectsByIsAllocated(boolean isAllocated) {
        Timestamp currentTime = DateTimeUtil.getCurrentTimestamp();
        // 找出已结束活动
        List<Project> projects = projectDao.findByEndTimeLessThanOrderByEndTime(currentTime);

        return projects
                .stream()
                .filter(p -> (allocationDao.findByProjectId(p.getId()) != null) == isAllocated)
                .map(this::convertProjectToIncomeVO)
                .collect(Collectors.toList());
    }

    private ProjectPrice convertVoToProjectPrice(ProjectPriceVO vo, Project project) {
        ProjectPrice projectPrice = new ProjectPrice();

        projectPrice.setProject(project);
        projectPrice.setSeatName(vo.getSeatName());
        projectPrice.setPrice(vo.getSeatPrice());
        projectPrice.setRow(vo.getRow());
        projectPrice.setColumn(vo.getColumn());
        // 行数列数相乘得座位总数
        projectPrice.setSeatNumber(vo.getRow() * vo.getColumn());

        return projectPrice;
    }

    @Override
    public Integer releaseProject(String identification, ProjectAddVO vo) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            throw new RuntimeException("场馆不存在");
        }
        if (vo.getPrices() == null || vo.getPrices().isEmpty()) {
            throw new RuntimeException("未填写座位售价信息");
        }

        Timestamp currentTime = DateTimeUtil.getCurrentTimestamp();
        Timestamp beginTime, endTime;
        try {
            beginTime = DateTimeUtil.convertStringToTimestamp(vo.getBeginTime());
            endTime = DateTimeUtil.convertStringToTimestamp(vo.getEndTime());
        } catch (Exception e) {
            throw new RuntimeException("日期格式错误");
        }

        if (beginTime.compareTo(endTime) > 0 || beginTime.compareTo(currentTime) < 0) {
            throw new RuntimeException("日期错误，请重新检查日期");
        }

        Project project = new Project();
        project.setName(vo.getName());
        project.setBeginTime(beginTime);
        project.setEndTime(endTime);
        project.setDescription(vo.getDescription());
        project.setVenue(venue);
        project.setType(vo.getType());
        project.setPriceList(vo.getPrices().stream().map(v -> convertVoToProjectPrice(v, project)).collect(Collectors.toList()));

        return projectDao.save(project);
    }

    @Override
    public void uploadProjectPoster(String identification, Integer projectId, File poster) {
        Venue venue = venueDao.findByIdentification(identification);
        if (venue == null) {
            throw new RuntimeException("场馆不存在");
        }
        Project project = projectDao.get(projectId);
        if (project == null) {
            throw new RuntimeException("活动不存在");
        }
        if (!project.getVenue().getId().equals(venue.getId())) {
            throw new RuntimeException("非本场馆举办活动，不可上传海报");
        }
        project.setPosterURL("/" + Constants.POSTER_DIR + "/" + projectId + "/" + System.currentTimeMillis());
        projectDao.save(project);
    }

    @Override
    public void allocateProject(Integer projectId, int ratio) {
        if (ratio < 0 || ratio > 100) {
            throw new RuntimeException("比例值错误");
        }
        Project project = projectDao.get(projectId);
        if (project == null) {
            throw new RuntimeException("活动不存在");
        }
        if (allocationDao.findByProjectId(projectId) != null) {
            throw new RuntimeException("活动已结算");
        }
        double totalIncome = payDao.sumMoneyByProjectId(project.getId());
        double platformIncome = ((int)totalIncome * ratio) / 100.0;
        double venueIncome = ((int)totalIncome * (100 - ratio)) / 100.0;

        Allocation allocation = new Allocation();
        allocation.setProjectId(projectId);
        allocation.setVenueId(project.getVenue().getId());
        allocation.setPlatformIncome(platformIncome);
        allocation.setVenueIncome(venueIncome);
        allocation.setRatio(ratio);
        allocation.setTime(DateTimeUtil.getCurrentTimestamp());

        allocationDao.save(allocation);
    }

}
