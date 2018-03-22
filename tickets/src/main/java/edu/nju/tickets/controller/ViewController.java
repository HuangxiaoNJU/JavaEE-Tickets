package edu.nju.tickets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/venue"})
    public String venue() {
        return "venue";
    }

    @GetMapping(value = {"/project"})
    public String project() {
        return "project";
    }

    @GetMapping("/user/order")
    public String userOrderForm() {
        return "orderForm";
    }

    @GetMapping("/user/person")
    public String person() {
        return "person";
    }

    @GetMapping("/venue/info")
    public String venueInfo() {
        return "venueInfo";
    }

    @GetMapping("/venue/project")
    public String venueProject() {
        return "venueProject";
    }

    @GetMapping("/venue/statistics")
    public String venueStatistics() {
        return "venueStatistics";
    }

    @GetMapping("/manager/check")
    public String managerCheck() {
        return "managerCheck";
    }

    @GetMapping("/manager/allocate")
    public String managerAllocate() {
        return "managerAllocate";
    }

    @GetMapping("/manager/statistics")
    public String managerStatistics() {
        return "managerStatistics";
    }

    @GetMapping("/order/detail")
    public String orderFormDetail() {
        return "orderFormDetail";
    }

    @GetMapping("/order/make")
    public String orderMake() {
        return "orderMake";
    }

    @GetMapping("/order/make-offline")
    public String orderMakeOffline() {
        return "orderMakeOffline";
    }

    @GetMapping("/project/detail")
    public String projectDetail() {
        return "projectDetail";
    }

}
