package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.dao.CalendarMapper;
import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    private final CalendarMapper calendarMapper;

    @Autowired
    public CalendarService (CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public List<CalendarDTO> calendarService () {
        List<CalendarDTO> result = calendarMapper.showCalendar();
        return result;
    }

}
