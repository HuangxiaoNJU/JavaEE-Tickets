package edu.nju.tickets.controller;

import edu.nju.tickets.service.VenueService;
import edu.nju.tickets.util.CookieUtil;
import edu.nju.tickets.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static edu.nju.tickets.util.Constants.CookieName.MANAGER_COOKIE_NAME;
import static edu.nju.tickets.util.Constants.CookieName.VENUE_COOKIE_NAME;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    @Resource
    private VenueService venueService;

    /**
     * 根据关键词搜索场馆
     *
     * @param keywords  关键词
     * @return          场馆列表
     */
    @GetMapping("/search")
    public ResponseResult<List<VenueInfoVO>> search(@RequestParam String keywords) {
        return new ResponseResult<>(true, "", new ArrayList<>(venueService.searchVenue(keywords)));
    }

    /**
     * 场馆注册
     *
     * @param vo        注册信息
     * @return          注册结果
     */
    @PostMapping
    public ResponseResult<Void> register(@RequestBody VenueRegisterVO vo) {
        try {
            venueService.register(vo);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "注册成功，请等待审核");
    }

    /**
     * 场馆登录
     *
     * @param email             登录邮箱
     * @param identification    识别码
     * @param response          HttpServletResponse：用于加入Cookie
     * @return                  登录结果
     */
    @PostMapping("/login")
    public ResponseResult<Void> login(@RequestParam(name = "username") String email,
                                      @RequestParam(name = "password") String identification,
                                      HttpServletResponse response) {
        boolean result = venueService.login(email, identification);
        if (result) {
            response.addCookie(CookieUtil.getCookie(VENUE_COOKIE_NAME, identification));
        }
        return new ResponseResult<>(result, result ? "登录成功": "登录失败");
    }

//    @PostMapping("/logout")
//    public ResponseResult<Void> logout(@CookieValue(name = VENUE_COOKIE_NAME, required = false) Cookie cookie,
//                                         HttpServletResponse response) {
//        if (cookie == null) {
//            return new ResponseResult<>(false, "已退出登录", null);
//        }
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        return new ResponseResult<>(true, "已退出登录", null);
//    }

    /**
     * 根据场馆id获取场馆信息
     *
     * @param id        场馆id
     * @return          场馆信息
     */
    @GetMapping("/id/{id}")
    public ResponseResult<VenueInfoVO> getVenueInfoById(@PathVariable Integer id) {
        VenueInfoVO vo = venueService.getVenueInfoById(id);
        if (vo == null) {
            return new ResponseResult<>(false, "场馆不存在");
        }
        return new ResponseResult<>(true, "", vo);
    }

    /**
     * 根据Cookie获取场馆信息
     *
     * @param identification    cookie中的识别码信息
     * @return                  场馆信息
     */
    @GetMapping("/cookie")
    public ResponseResult<VenueInfoVO> getVenueInfo(@CookieValue(value = VENUE_COOKIE_NAME, required = false) String identification) {
        if (identification == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        VenueInfoVO vo = venueService.getVenueInfoByIdentification(identification);
        if (vo == null) {
            return new ResponseResult<>(false, "场馆不存在");
        }
        return new ResponseResult<>(true, "", vo);
    }

    /**
     * 获取所有场馆信息
     *
     * @param property          属性（默认为id）
     * @param order             排序方式（asc|desc，默认为asc）
     * @return                  场馆列表
     */
    @GetMapping
    public ResponseResult<List<VenueInfoVO>> getVenues(@RequestParam(required = false, defaultValue = "id") String property,
                                                       @RequestParam(required = false, defaultValue = "asc") String order) {
        List<VenueInfoVO> res = new ArrayList<>(venueService.getVenues(property, order));
        return new ResponseResult<>(true, "", res);
    }

    /**
     * 获取申请注册场馆列表
     *
     * @param isChecked         是否被审核
     * @param managerName       cookie中的manager name
     * @return                  场馆列表
     */
    @GetMapping("/check/register/{isChecked}")
    public ResponseResult<List<VenueInfoVO>> getApplyRegisterVenues(@PathVariable boolean isChecked,
                                                                    @CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        List<VenueInfoVO> res = new ArrayList<>(venueService.getVenuesByIsChecked(isChecked));
        return new ResponseResult<>(true, "", res);
    }

    /**
     * 获取申请修改场馆列表
     *
     * @param isChecked         是否被审核
     * @param managerName       cookie中的manager name
     * @return                  场馆列表
     */
    @GetMapping("/check/modify/{isChecked}")
    public ResponseResult<List<VenueModifyInfoVO>> getApplyModifyVenues(@PathVariable boolean isChecked,
                                                                        @CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        List<VenueModifyInfoVO> res = new ArrayList<>(venueService.getVenueModifyInfo(isChecked));
        return new ResponseResult<>(true, "", res);
    }

    /**
     * 场馆申请修改信息
     *
     * @param identification    识别码
     * @param vo                修改信息
     * @return                  申请结果
     */
    @PostMapping("/modify")
    public ResponseResult<Void> applyForChangeInfo(@CookieValue(value = VENUE_COOKIE_NAME, required = false) String identification,
                                                   @RequestBody VenueChangeVO vo) {
        if (identification == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            venueService.applyForChangeInfo(identification, vo);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "申请修改成功，请等待审核");
    }

    /**
     * 审核场馆注册
     *
     * @param id                场馆id
     * @param isPass            是否通过注册
     * @param managerName       cookie中的manager name
     * @return                  审核结果
     */
    @PostMapping("/id/{id}")
    public ResponseResult<Void> checkVenueRegister(@PathVariable Integer id,
                                                   @RequestParam boolean isPass,
                                                   @CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            venueService.checkVenueRegister(id, isPass);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "注册审核成功");
    }

    /**
     * 审核场馆修改信息
     *
     * @param modifyId          修改id
     * @param isPass            是否通过
     * @param managerName       cookie中的manager name
     * @return                  审核结果
     */
    @PostMapping("/modify/{modifyId}")
    public ResponseResult<Void> checkVenueModify(@PathVariable Integer modifyId,
                                                 @RequestParam boolean isPass,
                                                 @CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            venueService.checkVenueModify(modifyId, isPass);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "审核成功，结果为：" + (isPass ? "通过" : "未通过"));
    }

    /**
     * 获取某场馆统计信息
     *
     * @param identification        cookie中的识别码信息
     * @return                      某场馆统计信息
     */
    @GetMapping("/statistics/cookie")
    public ResponseResult<VenueStatisticsVO> getVenueStatistics(@CookieValue(value = VENUE_COOKIE_NAME, required = false) String identification) {
        if (identification == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        return new ResponseResult<>(true, "", venueService.getVenueStatistics(identification));
    }

    /**
     * 获取所有场馆统计信息
     *
     * @param managerName           cookie中的manager name
     * @return                      所有场馆统计信息
     */
    @GetMapping("/statistics")
    public ResponseResult<VenuesStatisticsVO> getVenuesStatistics(@CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "请以管理员身份登录");
        }
        return new ResponseResult<>(true, "", venueService.getVenuesStatistics());
    }

    /**
     * 获取单个场馆统计信息
     *
     * @param identification        识别码
     * @return                      单个统计信息
     */
    @GetMapping("/individual/statistics")
    public ResponseResult<VenueIndividualStatistics> getIndividualVenueStatistics(@CookieValue(value = VENUE_COOKIE_NAME, required = false) String identification) {
        if (identification == null) {
            return new ResponseResult<>(false, "请以场馆身份登录");
        }
        try {
            VenueIndividualStatistics vo = venueService.getVenueIndividualStatistics(identification);
            return new ResponseResult<>(true, "", vo);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
    }

}
