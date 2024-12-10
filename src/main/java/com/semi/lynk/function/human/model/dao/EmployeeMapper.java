package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.dto.EmpAndDepDTO;
import com.semi.lynk.function.human.model.dto.EmployeeDTO;
import com.semi.lynk.function.human.model.dto.RegistHumDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDTO> employeeFullList();

    List<EmpAndDepDTO> joinListResult();

//    int registMapperEmp(RegistEmpDTO registEmpDTO);

    int registMapperhum(RegistHumDTO registHumDTO);

//    int registEmployee(Map<String, Object> map1);
////  int registHuman(Map<String, Object> map2);


}
