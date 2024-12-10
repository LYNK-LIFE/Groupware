package com.semi.lynk.function.electronic_payment.controller;


import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/payment/*")
public class PaymentController {

    private final PaymentService paymentService;
    private final MessageSource messageSource;

    @Autowired
    public PaymentController(PaymentService paymentService, MessageSource messageSource){
        this.paymentService = paymentService;
        this.messageSource = messageSource;
    }

    @GetMapping("/list")
    public String findPaymentList(Model model){

        List<ApproveDTO> approveList =paymentService.findAllPayment();

        for(ApproveDTO approve : approveList){
            System.out.println("approve = " + approve);

        }
        model.addAttribute("approveList",approveList);

        return "function/electronic_payment/list";
    }



}
