package com.semi.lynk.function.login.model.dao;

import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.model.dto.LoginLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {

    int addEmployee(EmpAddDTO empAddDTO);

    LoginDTO findByUsername(String empName);

    // 로그인 로깅
    void insertLoginLog(LoginLogDTO loginLogDTO);       // 로그 기록
    LoginLogDTO selectLatestLogByEmpNo(String empNo);   // 최근 로그인 시간
    List<LoginLogDTO> selectLogsByEmpNo(String empNo);  // 계정별 로그인 시간 목록

}
