package com.semi.lynk.model.dao.human;

import com.semi.lynk.model.dto.human.EmpAndDepDTO;
import com.semi.lynk.model.dto.human.EmployeeDTO;
import com.semi.lynk.model.dto.human.HumanDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDTO> employeeFullList();

    List<EmpAndDepDTO> joinListResult();

    int employeeRegist(HumanDTO humanDTO);
}
