package edu.nju.tickets.dao.impl;

import edu.nju.tickets.dao.PayDao;
import edu.nju.tickets.entity.Pay;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PayDaoImpl extends BaseDaoImpl<Pay, Integer> implements PayDao {

    @Override
    public double sumMoneyByProjectIdAndIsOnline(Integer projectId, boolean isOnline) {
        Double res = (Double) hibernateTemplate
                .find("select sum(p.money) from Pay p where p.projectId=? and p.online=?", projectId, isOnline)
                .iterator()
                .next();
        return res == null ? 0d : res;
    }

    @Override
    public double sumMoneyByProjectId(Integer projectId) {
        Double res = (Double) hibernateTemplate
                .find("select sum(p.money) from Pay p where p.projectId=?", projectId)
                .iterator()
                .next();
        return res == null ? 0d : res;
    }

    @Override
    public double sumMoneyByVenueIdAndIsOnline(Integer venueId, boolean isOnline) {
        Double res = (Double)
                hibernateTemplate
                .find("select sum(p.money) from Pay p where p.venueId=? and p.online=?", venueId, isOnline)
                .iterator()
                .next();
        return res == null ? 0d : res;
    }

    @Override
    public double sumMoneyByVenueId(Integer venueId) {
        Double res = (Double)
                hibernateTemplate
                .find("select sum(p.money) from Pay p where p.venueId=?", venueId)
                .iterator()
                .next();
        return res == null ? 0 : res;
    }

    @Override
    public List<Object[]> findProjectIncomeByVenueId(Integer venueId) {
        return (List<Object[]>) hibernateTemplate.find("select sum(p.money), p.projectId from Pay p where p.venueId=? group by p.projectId", venueId);
    }
}
