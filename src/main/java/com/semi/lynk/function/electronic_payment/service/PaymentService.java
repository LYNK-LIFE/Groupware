package com.semi.lynk.function.electronic_payment.service;

import com.semi.lynk.function.electronic_payment.model.dao.PaymentMapper;
import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeAndEctJoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;


    @Autowired
    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    // 결재자 목록 조회
    public List<EmployeeAndEctJoinDTO> findAllApprovers() {
        return paymentMapper.findAllApprover();
    }


    // 결재선 추가 처리
    public boolean addApprover(EmployeeAndEctJoinDTO employeeAndEctJoinDTO) {
        int result = paymentMapper.inserApprover(employeeAndEctJoinDTO);
        return result > 0; // 성공 여부 반환
    }


}



