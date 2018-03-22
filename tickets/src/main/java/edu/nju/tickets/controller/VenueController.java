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

    @GetMapping("/search")
    public ResponseResult<List<VenueInfoVO>> search(@RequestParam String keywords) {
        return new ResponseResult<>(true, "", new ArrayList<>(venueService.searchVenue(keywords)));
    }

    @PostMapping
    public ResponseResult<Void> register(@RequestBody VenueRegisterVO vo) {
        try {
            venueService.register(vo);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "注册成功，请等待审核");
    }

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

    @GetMapping("/id/{id}")
    public ResponseResult<VenueInfoVO> getVenueInfoById(@PathVariable Integer id) {
        VenueInfoVO vo = venueService.getVenueInfoById(id);
        if (vo == null) {
            return new ResponseResult<>(false, "场馆不存在");
        }
        return new ResponseResult<>(true, "", vo);
    }

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

    @GetMapping
    public ResponseResult<List<VenueInfoVO>> getVenues(@RequestParam(required = false, defaultValue = "id") String property,
                                                       @RequestParam(required = false, defaultValue = "asc") String order) {
        List<VenueInfoVO> res = new ArrayList<>(venueService.getVenues(property, order));
        return new ResponseResult<>(true, "", res);
    }

    @GetMapping("/check/register/{isChecked}")
    public ResponseResult<List<VenueInfoVO>> getApplyRegisterVenues(@PathVariable boolean isChecked,
                                                                    @CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        List<VenueInfoVO> res = new ArrayList<>(venueService.getUnCheckedVenues());
        return new ResponseResult<>(true, "", res);
    }

    @GetMapping("/check/modify/{isChecked}")
    public ResponseResult<List<VenueModifyInfoVO>> getApplyModifyVenues(@PathVariable boolean isChecked,
                                                                        @CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        List<VenueModifyInfoVO> res = new ArrayList<>(venueService.getVenueModifyInfo(isChecked));
        return new ResponseResult<>(true, "", res);
    }

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

    @GetMapping("/statistics/cookie")
    public ResponseResult<VenueStatisticsVO> getVenueStatistics(@CookieValue(value = VENUE_COOKIE_NAME, required = false) String identification) {
        if (identification == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        return new ResponseResult<>(true, "", venueService.getVenueStatistics(identification));
    }

    @GetMapping("/statistics")
    public ResponseResult<VenuesStatisticsVO> getVenuesStatistics(@CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        return new ResponseResult<>(true, "", venueService.getVenuesStatistics());
    }

}
