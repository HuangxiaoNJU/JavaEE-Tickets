package edu.nju.tickets.service;

import edu.nju.tickets.vo.OrderFormAddVO;
import edu.nju.tickets.vo.OrderFormInfoVO;

import java.util.List;

public interface OrderFormService {

    OrderFormInfoVO getOrderFormInfoById(final Integer id);

    List<OrderFormInfoVO> getOrderFormsByUser(final String email, final int state);

    OrderFormInfoVO makeOrderForm(final String email, final OrderFormAddVO vo);

    OrderFormInfoVO makeOrderFormOffline(final String identification, final OrderFormAddVO vo);

    Double getRealTimeOrderFormPrice(final String email, final OrderFormAddVO vo);

    void payForOrderForm(final String email, final Integer accountId, final Integer orderFormId);

    double refundOrderForm(final String email, final Integer orderFormId);

    void checkIn(final String identification, final Integer orderId);

    Boolean updateOrderFormScore(Integer orderId,int score);

}
