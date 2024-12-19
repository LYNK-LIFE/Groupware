package com.semi.lynk.function.management.controller;

import com.semi.lynk.function.management.model.dto.AccountDTO;
import com.semi.lynk.function.management.service.ManagedService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/management/*")
public class managedController {

    @Autowired
    private ManagedService managedService;

    // 관리자 페이지 진입
    // 활성화 계정 목록
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

    // 활성화 계정 목록 - 삭제
    @PostMapping("/deleteAccounts")
    public String deactivateAccounts(@RequestParam @Param("empIDs") List<String> empIDs, RedirectAttributes redirectAttributes) {
        System.out.println("controller 단 empIDs = " + empIDs);
        if (empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
            redirectAttributes.addFlashAttribute("message", "선택된 계정이 없습니다.");
            return "redirect:/management/activeAccountList";
        }

        // 선택된 계정들의 memberStatus를 3으로 업데이트
        managedService.deactivateAccounts(empIDs);

        // 삭제 후 삭제된 계정 목록 페이지로
        redirectAttributes.addFlashAttribute("message", "선택된 계정이 성공적으로 삭제되었습니다.");
        return "redirect:/management/inactiveAccountList";
    }

    // 비활성화 계정 목록
    @GetMapping("inactiveAccountList")
    public String inactiveAccountListPage(Model model) {
        // 비활성화 계정 개수 카운트와 삭제 계정 카운트값
        Map<String, Object> counts = managedService.getMemberStatusCountsInac();
        model.addAttribute("inactiveAcc", counts.get("inactiveAcc"));
        model.addAttribute("removedAcc", counts.get("removedAcc"));

        // 비활성화 계정 목록 조회
        List<Map<String, Object>> inactiveAccs = managedService.getInactiveEmployee();
        model.addAttribute("inactiveAccs", inactiveAccs);

        return "function/management/inactiveAccountList";
    }

    // 계정 수정
    @GetMapping("editAccount/{empID}")
    public String editAccountPage(@PathVariable("empID") String empID, Model model) {
        // empID를 기반으로 계정 정보를 조회
        AccountDTO accountInfo = managedService.getAccountByEmpID(empID);

        // 계정 정보를 모델에 추가하여 뷰로 전달
        model.addAttribute("accountInfo", accountInfo);

        // 계정 편집 페이지로 이동
        return "function/management/editAccount";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@ModelAttribute AccountDTO accountInfo) {
        String empID = accountInfo.getEmpID();
        String empName = accountInfo.getEmpName();
        String deptName = accountInfo.getDeptName();
        String position = accountInfo.getPosition();
        String email = accountInfo.getEmail();

        // 서비스 메서드를 호출하여 계정 정보 업데이트
        managedService.updateAccount(empID, empName, deptName, position, email);

        // 업데이트 후 목록 페이지로 리디렉션
        return "redirect:/management/activeAccountList";
    }



}
