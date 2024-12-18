package com.semi.lynk.function.management.controller;

import com.semi.lynk.function.management.service.ManagedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/management/*")
public class managedController {

    @Autowired
    private ManagedService managedService;

    // 관리자 페이지 진입
    @GetMapping("activeAccountList")
    public String activeAccountListPage(Model model) {
        // 활성화 계정 개수 카운트와 정지 계정 카운트값
        Map<String, Object> counts = managedService.getMemberStatusCounts();
        model.addAttribute("activeAcc", counts.get("activeAcc"));
        model.addAttribute("suspendedAcc", counts.get("suspendedAcc"));

        // 활성화 계정 목록 조회
        List<Map<String, Object>> activeAccs = managedService.getActiveEmployee();
        model.addAttribute("activeAccs", activeAccs);

        return "function/management/activeAccountList";
    }

}
