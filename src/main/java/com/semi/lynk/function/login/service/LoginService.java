package com.semi.lynk.function.login.service;

import com.semi.lynk.function.login.model.EmpDetails;
import com.semi.lynk.function.login.model.dao.LoginMapper;
import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.model.dto.LoginLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class LoginService implements UserDetailsService {

    //****************************************************************
    // 회원가입 관련 내용들
    //****************************************************************
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private LoginMapper loginMapper;

    @Transactional
    public int addEmployee(EmpAddDTO empAddDTO) {
        empAddDTO.setUserPass(encoder.encode(empAddDTO.getUserPass()));
        int result = loginMapper.addEmployee(empAddDTO);
        return result;
    }

    public LoginDTO findByUsername(String empName) {
        LoginDTO login = loginMapper.findByUsername(empName);
        if (login == null) {
            return null;
        } else {
            return login;
        }
    }

    //****************************************************************
    // 로그인 관련 내용들
    //****************************************************************
    public LoginDTO getLoginUsername(String empName) {
        return loginMapper.findByUsername(empName);
    }

    @Override
    public UserDetails loadUserByUsername(String empname) throws UsernameNotFoundException {
        LoginDTO login = findByUsername(empname);
        if (login == null) {
            throw new UsernameNotFoundException("사번 정보가 존재하지 않습니다.");
        }
        return new EmpDetails(login);
    }

}
