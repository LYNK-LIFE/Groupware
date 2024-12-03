package com.semi.lynk.model.service;

import com.semi.lynk.model.dao.EmployeeMapper;
import com.semi.lynk.model.dto.EmpAndDepDTO;
import com.semi.lynk.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<EmpAndDepDTO> showJoinResult() {

        return mapper.showMejoinResult();
    }
}
