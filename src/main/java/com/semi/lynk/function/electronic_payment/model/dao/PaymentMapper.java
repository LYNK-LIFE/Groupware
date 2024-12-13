package com.semi.lynk.function.electronic_payment.model.dao;

import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeAndEctJoinDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<EmployeeAndEctJoinDTO> findAllApprover();


    int inserApprover(EmployeeAndEctJoinDTO employeeAndEctJoinDTO);
}
