package edu.nju.tickets.controller;

import edu.nju.tickets.service.ManagerService;
import edu.nju.tickets.util.CookieUtil;
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

}
