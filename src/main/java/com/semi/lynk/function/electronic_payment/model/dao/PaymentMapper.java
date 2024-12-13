package com.semi.lynk.function.electronic_payment.model.dao;

import com.semi.lynk.function.electronic_payment.model.dto.ApproveDTO;
import com.semi.lynk.function.electronic_payment.model.dto.AuthorizationAndDepartmentAndEmployeeAndHumanResourceDTO;
import com.semi.lynk.function.electronic_payment.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PaymentMapper {


    List<AuthorizationAndDepartmentAndEmployeeAndHumanResourceDTO> findAllEmployees();

    int insertApprover(ApproveDTO approveDTO);


}
