package com.semi.lynk.function.login.service;

import com.semi.lynk.function.login.model.dao.LoginMapper;
import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

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

}
