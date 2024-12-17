package com.semi.lynk.function.management.controller;

import com.semi.lynk.function.management.service.ManagedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/*")
public class managedController {

    //****************************************************************
    // 관리자 페이지 진입
    //****************************************************************
    @Autowired
    private ManagedService managedService;

    @GetMapping("activeAccountList")
    public String activeAccountListPage() {
        return "/function/management/activeAccountList";
    }

}
