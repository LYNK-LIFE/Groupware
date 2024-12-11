package com.semi.lynk.function.electronic_payment.service;

import com.semi.lynk.function.electronic_payment.model.dao.PaymentMapper;
import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.model.dto.DraftDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;


    @Autowired
    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    public List<ApproveDTO> draft() {
        return paymentMapper.draftApprove();
    }

        // 모든 직원 리스트 가져오기 (결재선 추가 시 사용)
        public List<EmployeeDTO> getAllEmployees() {
            return paymentMapper.findAllEmployees();
        }

        // 결재선 추가 처리
        public boolean addApprover(ApproveDTO approveDTO) {
            int result = paymentMapper.insertApprover(approveDTO);
            return result > 0; // 성공 여부 반환
        }
    }



