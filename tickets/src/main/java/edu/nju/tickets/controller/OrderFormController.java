package edu.nju.tickets.controller;

import edu.nju.tickets.service.OrderFormService;
import edu.nju.tickets.vo.OrderFormAddVO;
import edu.nju.tickets.vo.OrderFormInfoVO;
import edu.nju.tickets.vo.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static edu.nju.tickets.util.Constants.CookieName.USER_COOKIE_NAME;
import static edu.nju.tickets.util.Constants.CookieName.VENUE_COOKIE_NAME;

@RestController
@RequestMapping("/api/orders")
public class OrderFormController {

    @Resource
    private OrderFormService orderFormService;

    @GetMapping("/id/{id}")
    public ResponseResult<OrderFormInfoVO> getOrderFormById(@PathVariable Integer id) {
        OrderFormInfoVO vo = orderFormService.getOrderFormInfoById(id);
        if (vo == null) {
            return new ResponseResult<>(false, "订单不存在");
        }
        return new ResponseResult<>(true, "", vo);
    }

    @GetMapping("/user")
    public ResponseResult<List<OrderFormInfoVO>> getOrderFormByUser(@CookieValue(name = USER_COOKIE_NAME, required = false) String email,
                                                                    @RequestParam(required = false, defaultValue = "-1") int state) {
        List<OrderFormInfoVO> orderForms = new ArrayList<>(orderFormService.getOrderFormsByUser(email, state));
        return new ResponseResult<>(true, "", orderForms);
    }

    @PostMapping("/price")
    public ResponseResult<Double> getRealTimeOrderPrice(@CookieValue(name = USER_COOKIE_NAME, required = false) String email,
                                                        @RequestBody OrderFormAddVO vo) {
        if (email == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        Double res = orderFormService.getRealTimeOrderFormPrice(email, vo);
        return new ResponseResult<>(res != null, "", res);
    }

    @PostMapping
    public ResponseResult<Integer> makeOrderForm(@CookieValue(name = USER_COOKIE_NAME, required = false) String email,
                                                 @CookieValue(name = VENUE_COOKIE_NAME, required = false) String identification,
                                                 @RequestBody OrderFormAddVO vo) {
        if (email == null && identification == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            OrderFormInfoVO res;
            if (email != null) {
                res = orderFormService.makeOrderForm(email, vo);
            } else {
                res = orderFormService.makeOrderFormOffline(identification, vo);
            }
            return new ResponseResult<>(true, "下单成功", res.getId());
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
    }

    @PostMapping("/pay/id/{id}")
    public ResponseResult<Void> payForOrderForm(@PathVariable Integer id,
                                                @RequestParam Integer accountId,
                                                @CookieValue(name = USER_COOKIE_NAME, required = false) String email) {
        if (email == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        try {
            orderFormService.payForOrderForm(email, accountId, id);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "支付成功");
    }

    @PostMapping("/refund/id/{id}")
    public ResponseResult<Void> refundOrderForm(@PathVariable Integer id,
                                                @CookieValue(value = USER_COOKIE_NAME, required = false) String email) {
        if (email == null) {
            return new ResponseResult<>(false, "用户尚未登录");
        }
        try {
            double refundMoney = orderFormService.refundOrderForm(email, id);
            return new ResponseResult<>(true, "退款成功，退还金额" + refundMoney + "元");

        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
    }

    @PostMapping("/check-in")
    public ResponseResult<Void> checkIn(@CookieValue(value = VENUE_COOKIE_NAME, required = false) String identification,
                                        @RequestParam Integer id) {
        if (identification == null) {
            return new ResponseResult<>(false, "场馆尚未登录");
        }
        try {
            orderFormService.checkIn(identification, id);
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
        return new ResponseResult<>(true, "检票成功");
    }

}
