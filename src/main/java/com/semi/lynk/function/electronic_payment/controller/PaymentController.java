package com.semi.lynk.function.electronic_payment.controller;

import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        public String general() {
            // 일반 품의서 페이지로 이동
            return "function/electronic_payment/general";
            // 실제 템플릿 경로: resources/templates/function/electronic_payment/general.html
        }


    }





