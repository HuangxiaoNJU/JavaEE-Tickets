package edu.nju.tickets.controller;

import edu.nju.tickets.service.ManagerService;
import edu.nju.tickets.util.CookieUtil;
import edu.nju.tickets.vo.PlatformStatisticsVO;
import edu.nju.tickets.vo.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static edu.nju.tickets.util.Constants.CookieName.MANAGER_COOKIE_NAME;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    /**
     * 经理登录
     *
     * @param managerName       经理名
     * @param password          密码
     * @param response          HttpServletResponse：用于添加cookie
     * @return                  登录结果
     */
    @PostMapping("/login")
    public ResponseResult<Void> login(@RequestParam(name = "username") String managerName,
                                      @RequestParam(name = "password") String password,
                                      HttpServletResponse response) {
        boolean result = managerService.login(managerName, password);
        // 登录成功设置cookie
        if (result) {
            response.addCookie(CookieUtil.getCookie(MANAGER_COOKIE_NAME, managerName));
        }
        return new ResponseResult<>(result, result ? "登录成功": "登录失败", null);
    }

//    public ResponseResult<Void> handleVenueRegister(@CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName,
//                                                    @RequestParam Integer venueId,
//                                                    @RequestParam boolean isPass) {
//        return null;
//    }

    @GetMapping("/statistics")
    public ResponseResult<PlatformStatisticsVO> getPlatformStatistics(@CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "请以管理员身份登录");
        }
        try {
            PlatformStatisticsVO vo = managerService.getPlatformStatistics(managerName);
            return new ResponseResult<>(true, "", vo);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseResult<>(false, e.getMessage());
        }
    }

}
