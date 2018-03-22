package edu.nju.tickets.service;

import edu.nju.tickets.vo.AccountInfoVO;

import java.util.List;

public interface AccountService {

    List<AccountInfoVO> getAccountInfoByUser(String email);

}
