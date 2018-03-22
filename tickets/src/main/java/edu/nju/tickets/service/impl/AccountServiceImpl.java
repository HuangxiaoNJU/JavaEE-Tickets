package edu.nju.tickets.service.impl;

import edu.nju.tickets.dao.AccountDao;
import edu.nju.tickets.dao.UserDao;
import edu.nju.tickets.entity.Account;
import edu.nju.tickets.entity.User;
import edu.nju.tickets.service.AccountService;
import edu.nju.tickets.vo.AccountInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;
    @Resource
    private UserDao userDao;

    private AccountInfoVO convertAccountToVO(Account account) {
        if (account == null) {
            return null;
        }
        AccountInfoVO vo = new AccountInfoVO();
        vo.setId(account.getId());
        vo.setNumber(account.getAccountNumber());
        vo.setBalance(account.getBalance());
        vo.setType(account.getType());
        return vo;
    }

    @Override
    public List<AccountInfoVO> getAccountInfoByUser(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            return new ArrayList<>();
        }
        List<Account> accounts = accountDao.findByUserId(user.getId());
        return accounts.stream().map(this::convertAccountToVO).collect(Collectors.toList());
    }

}
