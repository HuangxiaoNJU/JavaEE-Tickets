package edu.nju.tickets.controller;

import com.sun.mail.smtp.SMTPAddressFailedException;
import edu.nju.tickets.service.UserService;
import edu.nju.tickets.service.VenueService;
import edu.nju.tickets.util.CookieUtil;
import edu.nju.tickets.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static edu.nju.tickets.util.Constants.CookieName.*;
import static edu.nju.tickets.util.Constants.Identity.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private VenueService venueService;

    /**
     * 获取验证码
     *
     * @param email     邮箱
     * @return          获取结果
     */
    @GetMapping("/verification")
    public ResponseResult<Void> sendVerificationCode(@RequestParam String email) {
        try {
            userService.sendVerificationCode(email);
        } catch (SMTPAddressFailedException e) {
            e.printStackTrace();
            return new ResponseResult<>(false, "邮箱不存在");
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "");
    }

    /**
     * 获取登录者身份
     *
     * @param userCookie        用户cookie
     * @param venueCookie       场馆cookie
     * @param managerCookie     经理cookie
     * @return                  身份
     */
    @GetMapping("/identity")
    public ResponseResult<String> checkIdentity(@CookieValue(name = USER_COOKIE_NAME, required = false) Cookie userCookie,
                                                @CookieValue(name = VENUE_COOKIE_NAME, required = false) Cookie venueCookie,
                                                @CookieValue(name = MANAGER_COOKIE_NAME, required = false) Cookie managerCookie) {
        ResponseResult<String> result = new ResponseResult<>(true, VISITOR, "");
        if (userCookie != null) {
            result.setMessage(USER);
            result.setData(userService.getUserInfo(userCookie.getValue()).getNickname());
        }
        if (venueCookie != null) {
            result.setMessage(VENUE);
            result.setData(venueService.getVenueInfoByIdentification(venueCookie.getValue()).getName());
        }
        if (managerCookie != null) {
            result.setMessage(MANAGER);
            result.setData(managerCookie.getValue());
        }
        return result;
    }

    /**
     * 用户注册
     *
     * @param vo        注册信息
     * @return          注册结果
     */
    @PostMapping
    public ResponseResult<Void> register(@RequestBody UserRegisterVO vo) {
        try {
            userService.register(vo);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "注册成功");
    }

    /**
     * 用户登录
     *
     * @param email     邮箱
     * @param password  密码
     * @param resp      HttpServletResponse：用于加入cookie
     * @return          登录结果
     */
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestParam(name = "username") String email,
                                        @RequestParam(name = "password") String password,
                                        HttpServletResponse resp) {
        UserInfoVO vo = userService.login(email, password);
        // 登录成功设置cookie
        if (vo == null) {
            return new ResponseResult<>(false, "登录失败");
        }
        resp.addCookie(CookieUtil.getCookie(USER_COOKIE_NAME, email));
        return new ResponseResult<>(true, "登录成功", vo.getNickname());
    }

    /**
     * 登出
     *
     * @param userCookie        用户cookie
     * @param venueCookie       场馆cookie
     * @param managerCookie     经理cookie
     * @param response          HttpServletResponse：用于删除cookie
     * @return
     */
    @PostMapping("/logout")
    public ResponseResult<Void> logout(@CookieValue(name = USER_COOKIE_NAME, required = false) Cookie userCookie,
                                       @CookieValue(name = VENUE_COOKIE_NAME, required = false) Cookie venueCookie,
                                       @CookieValue(name = MANAGER_COOKIE_NAME, required = false) Cookie managerCookie,
                                       HttpServletResponse response) {
        boolean isLogout = true;
        Cookie cookie = null;
        if (userCookie != null) {
            isLogout = false;
            cookie = userCookie;
        }
        if (venueCookie != null) {
            isLogout = false;
            cookie = venueCookie;
        }
        if (managerCookie != null) {
            isLogout = false;
            cookie = managerCookie;
        }
        if (isLogout) {
            return new ResponseResult<>(false, "已退出登录");
        }
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseResult<>(true, "登出成功");
    }

    /**
     * 获取用户信息
     *
     * @param email     邮箱
     * @return          用户信息
     */
    @GetMapping("/email")
    public ResponseResult<UserInfoVO> getUserInfo(@RequestParam String email) {
        UserInfoVO vo = userService.getUserInfo(email);
        return new ResponseResult<>(vo != null, "", vo);
    }

    /**
     * 获取用户信息（根据cookie）
     *
     * @param email     cookie中用户email
     * @return          用户信息
     */
    @GetMapping("/cookie")
    public ResponseResult<UserInfoVO> getUserInfoByCookie(@CookieValue(value = USER_COOKIE_NAME, required = false) String email) {
        if (email == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        UserInfoVO vo = userService.getUserInfo(email);
        return new ResponseResult<>(vo != null, "", vo);
    }

    /**
     * 用户修改个人信息（仅支持修改昵称）
     *
     * @param nickname      修改后昵称
     * @param email         cookie中用户邮箱
     * @return              修改结果
     */
    @PostMapping("/modify")
    public ResponseResult<Void> changeUserInfo(@RequestParam String nickname,
                                               @CookieValue(value = USER_COOKIE_NAME, required = false) String email) {
        if (email == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            userService.changeUserInfo(email, nickname);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "修改成功");
    }

    /**
     * 获取用户所有优惠券
     *
     * @param email     cookie中用户email
     * @return          优惠券信息列表
     */
    @GetMapping("/coupons")
    public ResponseResult<List<UserCouponVO>> getUserCoupons(@CookieValue(value = USER_COOKIE_NAME, required = false) String email) {
        if (email == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        List<UserCouponVO> res = new ArrayList<>(userService.getUserCoupons(email));
        return new ResponseResult<>(true, "", res);
    }

    /**
     * 兑换优惠券
     *
     * @param email     cookie中用户email
     * @param couponId  优惠券id
     * @param number    兑换数量
     * @return          兑换结果
     */
    @PostMapping("/coupons")
    public ResponseResult<Void> exchangeCoupon(@CookieValue(value = USER_COOKIE_NAME, required = false) String email,
                                               @RequestParam Integer couponId,
                                               @RequestParam(defaultValue = "1") int number) {
        if (email == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            userService.exchangeCoupon(email, couponId, number);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "兑换成功");
    }

    /**
     * 注销用户
     *
     * @param userCookie    用户cookie
     * @param response      HttpServletResponse：用于删除cookie
     * @return              注销结果
     */
    @DeleteMapping
    public ResponseResult<Void> cancelUser(@CookieValue(value = USER_COOKIE_NAME, required = false) Cookie userCookie,
                                           HttpServletResponse response) {
        if (userCookie == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        boolean res = userService.cancel(userCookie.getValue());
        if (!res) {
            return new ResponseResult<>(false, "注销失败");
        }
        userCookie.setPath("/");
        userCookie.setMaxAge(0);
        response.addCookie(userCookie);
        return new ResponseResult<>(true, "注销成功");
    }

    /**
     * 获取所有用户统计信息
     *
     * @param managerName       cookie中manager name
     * @return                  所有用户统计信息
     */
    @GetMapping("/statistics")
    public ResponseResult<UsersStatisticsVO> getUsersStatistics(@CookieValue(value = MANAGER_COOKIE_NAME, required = false) String managerName) {
        if (managerName == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        UsersStatisticsVO vo = userService.getUsersStatistics();
        return new ResponseResult<>(true, "", vo);
    }

    /**
     * 获取某用户个人统计信息
     *
     * @param email             cookie中用户邮箱
     * @return                  用户个人统计信息
     */
    @GetMapping("/individual/statistics")
    public ResponseResult<IndividualStatisticsVO> getUserStatistics(@CookieValue(value = USER_COOKIE_NAME, required = false) String email) {
        if (email == null) {
            return new ResponseResult<>(false, "用户尚未登录");
        }
        try {
            IndividualStatisticsVO vo = userService.getIndividualStatistics(email);
            return new ResponseResult<>(true, "", vo);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
    }

}
