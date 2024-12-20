package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
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

    public List<CalendarDTO> myAppStatusService() {
        List<CalendarDTO> appStatus = calendarMapper.showMyAppStatus();
        return appStatus;
    }

    public List<VacationApplicationDTO> vacationStatus() {

        return calendarMapper.vacationAppMapper();
    }

    public int vacAppService(VacationApplicationDTO vacationApplicationDTO) {
        int result = calendarMapper.vacAppUpdateMapper(vacationApplicationDTO);

        return result >= 1 ? 1 : 0;
    }
}
