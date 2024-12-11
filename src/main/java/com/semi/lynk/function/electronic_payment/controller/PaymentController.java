package com.semi.lynk.function.electronic_payment.controller;

import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeDTO;
import com.semi.lynk.function.electronic_payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        // 서비스에서 결재 리스트 가져오기
        List<ApproveDTO> approveList = paymentService.draft();
        // 모델에 데이터 추가
        model.addAttribute("approveList", approveList);
        // 템플릿 경로 반환 (정확한 경로 확인 필요)
        return "function/electronic_payment/list";
    }


    @GetMapping("/general")
        public String general(Model model) {
            // 일반 품의서 페이지로 이동
        List<EmployeeDTO> employeeList = paymentService.getAllEmployees();
        model.addAttribute("employeeList", employeeList);
            return "function/electronic_payment/general";
            // 실제 템플릿 경로: resources/templates/function/electronic_payment/general.html
        }

    // 결재선 추가 페이지로 이동
    @GetMapping("/addapprover")
    public String addApproverPage(Model model) {
        // 추가 가능한 사용자 리스트 가져오기
        List<EmployeeDTO> employeeList = paymentService.getAllEmployees();
        model.addAttribute("employeeList", employeeList);
        return "addapprover";
    }

    // 결재선 추가 처리
    @PostMapping("/addapprover")
    public String addApprover(@ModelAttribute ApproveDTO approveDTO,
                              RedirectAttributes redirectAttributes) {
        // 결재선 추가
        boolean isAdded = paymentService.addApprover(approveDTO);

        // 결과 메시지 전달
        if (isAdded) {
            redirectAttributes.addFlashAttribute
                    ("message", "결재선이 성공적으로 추가되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute
                    ("message", "결재선 추가에 실패했습니다.");
        }

        // 결재 리스트 페이지로 리다이렉트
        return "redirect:/payment/list";
    }
}






