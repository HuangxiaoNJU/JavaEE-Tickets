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

    /**
     * 获取订单信息
     *
     * @param id        订单id
     * @return          订单信息
     */
    @GetMapping("/id/{id}")
    public ResponseResult<OrderFormInfoVO> getOrderFormById(@PathVariable Integer id) {
        OrderFormInfoVO vo = orderFormService.getOrderFormInfoById(id);
        if (vo == null) {
            return new ResponseResult<>(false, "订单不存在");
        }
        return new ResponseResult<>(true, "", vo);
    }

    /**
     * 获取用户订单
     *
     * @param email     cookie中用户邮箱
     * @param state     订单状态码
     * @return          订单列表
     */
    @GetMapping("/user")
    public ResponseResult<List<OrderFormInfoVO>> getOrderFormByUser(@CookieValue(name = USER_COOKIE_NAME, required = false) String email,
                                                                    @RequestParam(required = false, defaultValue = "-1") int state) {
        List<OrderFormInfoVO> orderForms = new ArrayList<>(orderFormService.getOrderFormsByUser(email, state));
        return new ResponseResult<>(true, "", orderForms);
    }

    /**
     * 计算订单实时价格
     *
     * @param email     cookie中用户邮箱
     * @param vo        订单信息
     * @return          订单价格
     */
    @PostMapping("/price")
    public ResponseResult<Double> getRealTimeOrderPrice(@CookieValue(name = USER_COOKIE_NAME, required = false) String email,
                                                        @RequestBody OrderFormAddVO vo) {
        if (email == null) {
            return new ResponseResult<>(false, "您尚未登录");
        }
        Double res = orderFormService.getRealTimeOrderFormPrice(email, vo);
        return new ResponseResult<>(res != null, "", res);
    }

    /**
     * 用户/场馆下单
     *
     * @param email             cookie中用户邮箱
     * @param identification    cookie中场馆识别码
     * @param vo                订单信息
     * @return                  下单结果
     */
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
                // 线上下单
                res = orderFormService.makeOrderForm(email, vo);
            } else {
                // 线下下单
                res = orderFormService.makeOrderFormOffline(identification, vo);
            }
            return new ResponseResult<>(true, "下单成功", res.getId());
        } catch (RuntimeException e) {
            return new ResponseResult<>(false, e.getMessage());
        }
    }

    /**
     * 支付订单
     *
     * @param id            订单id
     * @param accountId     账户id
     * @param email         cookie中用户邮箱
     * @return              支付结果
     */
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

    /**
     * 用户订单退款
     *
     * @param id        订单id
     * @param email     cookie中用户邮箱
     * @return          退款结果
     */
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

    /**
     * 场馆检票
     *
     * @param identification    cookie中场馆识别码
     * @param id                订单id
     * @return                  检票结果
     */
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
