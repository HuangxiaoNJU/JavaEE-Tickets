package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.AccountDao;
import edu.nju.tickets.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account, Integer> implements AccountDao {

    @Override
    public List<Account> findByUserId(Integer userId) {
        return find("from Account a where a.userId=?", userId);
    }

}
