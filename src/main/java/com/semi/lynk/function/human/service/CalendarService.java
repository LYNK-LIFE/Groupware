package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import com.semi.lynk.function.human.model.dto.CalendarDTO;
import com.semi.lynk.function.human.model.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public CalendarService (EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public List<Event> calendarService (CalendarDTO calendarDTO) {
        List<Event> result = employeeMapper.calendarMapper();
        return result;
    }

}
