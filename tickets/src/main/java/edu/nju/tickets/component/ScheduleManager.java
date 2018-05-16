package edu.nju.tickets.component;

import edu.nju.tickets.service.OrderFormStateChange;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class ScheduleManager {

    @Resource
    private OrderFormStateChange orderFormStateChange;

    /**
     * 定时检查是否有超时未付款订单
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    private void handleExceedOrders() {
        System.out.println(LocalDateTime.now() + ": 检查未支付订单");
        orderFormStateChange.convertNotPaidToCanceled();
    }

    /**
     * 定时检查是否有待分配订单
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    private void handleNotAllocatedOrders() {
        System.out.println(LocalDateTime.now() + ": 检查未分配订单");
        orderFormStateChange.convertNotAllocatedToFinished();
    }

}
