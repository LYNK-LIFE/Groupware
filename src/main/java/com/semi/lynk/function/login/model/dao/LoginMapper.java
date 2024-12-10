package com.semi.lynk.function.login.model.dao;

import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    int addEmployee(EmpAddDTO empAddDTO);

    LoginDTO findByUsername(String empName);
}
