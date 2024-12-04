package com.semi.lynk.service.human;

import com.semi.lynk.model.dao.human.EmployeeMapper;
import com.semi.lynk.model.dto.human.EmpAndDepDTO;
import com.semi.lynk.model.dto.human.EmployeeDTO;
import com.semi.lynk.model.dto.human.HumanDTO;
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

    public List<EmpAndDepDTO> joinList() {

        return mapper.joinListResult();
    }

    public int humanResister(HumanDTO humanDTO) {

        int result = mapper.employeeRegist(humanDTO);
        return result;
    }
}
