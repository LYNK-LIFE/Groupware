package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.dto.EmpAndDepDTO;
import com.semi.lynk.function.human.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDTO> employeeFullList();

    List<EmpAndDepDTO> joinListResult();

    int registMapperEmp(Map<String, Object> map1);

    int registMapperhum(Map<String, Object> map2);

//    int registEmployee(Map<String, Object> map1);
////  int registHuman(Map<String, Object> map2);


}
