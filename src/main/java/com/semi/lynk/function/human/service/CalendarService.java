package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

    private final EmployeeMapper employeeMapper;

    public CalendarService (EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;

    }

}
