package com.semi.lynk.function.electronic_payment.model.dao;

import com.semi.lynk.function.electronic_payment.model.dto.DepAndEmpAndHumDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<DepAndEmpAndHumDTO> findAllApprover();


    int inserApprover(DepAndEmpAndHumDTO employeeAndEctJoinDTO);
}
