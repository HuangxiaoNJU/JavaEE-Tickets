package edu.nju.tickets.controller;

import edu.nju.tickets.service.ProjectService;
import edu.nju.tickets.vo.ProjectAddVO;
import edu.nju.tickets.vo.ProjectIncomeVO;
import edu.nju.tickets.vo.ProjectInfoVO;
import edu.nju.tickets.vo.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static edu.nju.tickets.util.Constants.CookieName.MANAGER_COOKIE_NAME;
import static edu.nju.tickets.util.Constants.CookieName.VENUE_COOKIE_NAME;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 搜索活动
     *
     * @param keywords      关键词
     * @return              活动列表
     */
    @GetMapping("/search")
    public ResponseResult<List<ProjectInfoVO>> getProjectInfoById(@RequestParam String keywords) {
        return new ResponseResult<>(true, "", new ArrayList<>(projectService.searchProject(keywords)));
    }

    /**
     * 获取活动信息
     *
     * @param id            活动id
     * @return              活动信息
     */
    @GetMapping("/id/{id}")
    public ResponseResult<ProjectInfoVO> getProjectInfoById(@PathVariable Integer id) {
        ProjectInfoVO vo = projectService.getProjectInfo(id);
        if (vo == null) {
            return new ResponseResult<>(false, "活动不存在");
        }
        return new ResponseResult<>(true, "", vo);
    }

    /**
     * 获取所有活动
     *
     * @param property      排序属性（默认为id）
     * @param order         顺序（asc|desc，默认为desc）
     * @return              活动列表
     */
    @GetMapping
    public ResponseResult<List<ProjectInfoVO>> getProjects(@RequestParam(required = false, defaultValue = "id") String property,
                                                           @RequestParam(required = false, defaultValue = "desc") String order) {
        List<ProjectInfoVO> projects = new ArrayList<>(projectService.getProjects(property, order));
        return new ResponseResult<>(true, "", projects);
    }

    /**
     * 获取场馆活动
     *
     * @param venueId       场馆id
     * @param state         活动进行状态
     * @return              活动列表
     */
    @GetMapping("/venue/{venueId}")
    public ResponseResult<List<ProjectInfoVO>> getProjectsByVenue(@PathVariable Integer venueId,
                                                                  @RequestParam(required = false) String state) {

        List<ProjectInfoVO> projects = new ArrayList<>(projectService.getProjectsByVenue(venueId, state));
        return new ResponseResult<>(true, "", projects);
    }

    /**
     * 获取场馆活动（通过cookie）
     *
     * @param identification    cookie中识别码
     * @param state             活动进行状态
     * @return                  活动列表
     */
    @GetMapping("/venue/cookie")
    public ResponseResult<List<ProjectInfoVO>> getProjectsByVenue(@CookieValue(value = VENUE_COOKIE_NAME, required = false) String identification,
                                                                  @RequestParam(required = false) String state) {
        if (identification == null ) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        List<ProjectInfoVO> projects = new ArrayList<>(projectService.getProjectsByVenue(identification, state));
        return new ResponseResult<>(true, "", projects);
    }

    /**
     * 获取场馆收入情况
     *
     * @param isAllocated       是否分配收入
     * @param managerName       cookie中manager name
     * @return                  场馆收入信息列表
     */
    @GetMapping("/allocate")
    public ResponseResult<List<ProjectIncomeVO>> getProjectsByIsAllocated(@RequestParam boolean isAllocated,
                                                                          @CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        List<ProjectIncomeVO> res = new ArrayList<>(projectService.getProjectsByIsAllocated(isAllocated));

        return new ResponseResult<>(true, "", res);
    }

    /**
     * 发布活动
     *
     * @param identification    cookie中场馆识别码
     * @param vo                活动发布信息
     * @return                  发布结果
     */
    @PostMapping
    public ResponseResult<Void> releaseProject(@CookieValue(name = VENUE_COOKIE_NAME, required = false) String identification,
                                               @RequestBody ProjectAddVO vo) {
        if (identification == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            projectService.releaseProject(identification, vo);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "发布成功");
    }

    /**
     * 分配收入
     *
     * @param managerName       cookie中manager name
     * @param projectId         活动id
     * @param ratio             平台所得比例（单位：%）
     * @return                  分配结果
     */
    @PostMapping("/allocate")
    public ResponseResult<Void> allocateProject(@CookieValue(name = MANAGER_COOKIE_NAME, required = false) String managerName,
                                                @RequestParam Integer projectId,
                                                @RequestParam int ratio) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            projectService.allocateProject(projectId, ratio);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "结算成功");
    }

}
