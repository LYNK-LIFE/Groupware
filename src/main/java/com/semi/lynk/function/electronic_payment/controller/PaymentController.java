package com.semi.lynk.function.electronic_payment.controller;

import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeDTO;
import com.semi.lynk.function.electronic_payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/list")
    public String draft(Model model) {

        List<ApproveDTO> approveList = paymentService.draft();
        model.addAttribute("approveList", approveList);

        return "function/electronic_payment/list";
    }

    @GetMapping("/general")
    public String general(Model model) {
        List<EmployeeDTO> employeeList = paymentService.getAllEmployees();
        model.addAttribute("employeeList", employeeList);
        return "function/electronic_payment/general"; // HTML 템플릿 경로
    }

    @PostMapping("/addapprover")
    public String addApprover(@ModelAttribute ApproveDTO approveDTO,
                              RedirectAttributes redirectAttributes) {
        boolean isAdded = paymentService.addApprover(approveDTO);

        if (isAdded) {
            redirectAttributes.addFlashAttribute("message", "결재선이 성공적으로 추가되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "결재선 추가에 실패했습니다.");
        }
        return "redirect:/payment/list";
    }

    @ResponseBody
    @GetMapping("/approvers")
    public List<EmployeeDTO> getApprovers() {
        // JSON 데이터 반환 (기안 승인 권한자만 필터링 필요)
        return paymentService.getApprovers();
    }
}
