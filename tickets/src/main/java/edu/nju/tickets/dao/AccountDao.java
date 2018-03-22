package edu.nju.tickets.dao;

import edu.nju.tickets.entity.Account;

import java.util.List;

public interface AccountDao extends BaseDao<Account, Integer> {

    List<Account> findByUserId(Integer userId);

}
