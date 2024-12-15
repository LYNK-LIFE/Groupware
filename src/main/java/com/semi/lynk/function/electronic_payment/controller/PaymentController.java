package com.semi.lynk.function.electronic_payment.controller;

import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.model.dto.DepAndEmpAndHumDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeAndEctJoinDTO;
import com.semi.lynk.function.electronic_payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/approvers")
    @ResponseBody // JSON 데이터를 반환하기 위해 추가
    public List<DepAndEmpAndHumDTO> findAllApprovers(){
        return paymentService.findAllApprovers();
    }

    @GetMapping("/list")
    public String draft() {
        return "function/electronic_payment/list";
    }

    @GetMapping("/general")
    public String general() {return "function/electronic_payment/general";}


    @PostMapping("/add-approver")
    public String addApprover(@ModelAttribute DepAndEmpAndHumDTO depAndEmpAndHumDTO,
                              RedirectAttributes redirectAttributes) {
        boolean isAdded = paymentService.addApprover(depAndEmpAndHumDTO);

        if (isAdded) {
            redirectAttributes.addFlashAttribute("message", "결재선이 성공적으로 추가되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "결재선 추가에 실패했습니다.");
        }
        return "redirect:/payment/list";
    }


}
