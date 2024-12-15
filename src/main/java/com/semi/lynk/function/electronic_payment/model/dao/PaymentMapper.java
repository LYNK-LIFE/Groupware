package com.semi.lynk.function.electronic_payment.model.dao;

import com.semi.lynk.function.electronic_payment.model.dto.DepAndEmpAndHumDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentMapper {

    List<DepAndEmpAndHumDTO> findAllApprover();

    int insertApprover(DepAndEmpAndHumDTO employeeAndEctJoinDTO);

}
