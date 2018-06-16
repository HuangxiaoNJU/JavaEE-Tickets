package edu.nju.tickets.controller;

import edu.nju.tickets.service.AccountService;
import edu.nju.tickets.vo.AccountInfoVO;
import edu.nju.tickets.vo.ResponseResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static edu.nju.tickets.util.Constants.CookieName.USER_COOKIE_NAME;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 获取某用户所有账户信息
     *
     * @param email     cookie中用户email
     * @return          账户列表
     */
    @GetMapping
    public ResponseResult<List<AccountInfoVO>> getUserAccounts(@CookieValue(value = USER_COOKIE_NAME, required = false) String email) {
        if (email == null) {
            return new ResponseResult<>(false, "用户未登录");
        }
        List<AccountInfoVO> accounts = new ArrayList<>(accountService.getAccountInfoByUser(email));
        return new ResponseResult<>(true, "", accounts);
    }

}
