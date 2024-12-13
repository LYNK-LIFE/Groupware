package com.semi.lynk.function.electronic_payment.service;

import com.semi.lynk.function.electronic_payment.model.dao.PaymentMapper;
import com.semi.lynk.function.electronic_payment.model.dto.DepAndEmpAndHumDTO;
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
    public List<DepAndEmpAndHumDTO> findAllApprovers() {
        return paymentMapper.findAllApprover();
    }


    // 결재선 추가 처리
    public boolean addApprover(DepAndEmpAndHumDTO depAndEmpAndHumDTO) {
        int result = paymentMapper.inserApprover(depAndEmpAndHumDTO);
        return result > 0; // 성공 여부 반환
    }


}



