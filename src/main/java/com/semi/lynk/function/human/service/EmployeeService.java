package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import com.semi.lynk.function.human.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeService {

    private EmployeeMapper mapper;

    @Autowired
    public EmployeeService (EmployeeMapper mapper) {
        this.mapper = mapper;
    }


    public List<EmployeeDTO> employeeList () {

        return mapper.employeeFullList();
    }

    // 인사 등록 창에 인사 등록 안 된 애들 조회해주는 거
    public List<EmpAndDepDTO> joinList() {

        return mapper.joinListResult();
    }


    @Transactional
    public int humanRegist (RegistHumDTO registHumDTO) {

        int result2 = mapper.registMapperhum(registHumDTO);


        return result2 >= 1 ? 1 : 0;


    }

}
