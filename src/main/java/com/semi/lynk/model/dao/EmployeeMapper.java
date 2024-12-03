package com.semi.lynk.model.dao;

import com.semi.lynk.model.dto.EmpAndDepDTO;
import com.semi.lynk.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDTO> employeeFullList();

    List<EmpAndDepDTO> showMejoinResult();

}
