package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.calendar.OverTimeApplicationDTO;
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
        int result1 = calendarMapper.vacAppUpdateMapper(vacationApplicationDTO);
        int result2 = calendarMapper.vacAppInsertMapper1(vacationApplicationDTO);
        int result3 = calendarMapper.vacAppInsertMapper2(vacationApplicationDTO);

        return (result1 >= 1) && (result2 >= 1) && (result3 >= 1) ? 1 : 0;
    }

    public List<OverTimeApplicationDTO> overTimeAppService() {
        return calendarMapper.overTimeAppMapper();
    }

    public int overTimeAppDataService(OverTimeApplicationDTO overTimeDTO) {
        int result = calendarMapper.overTimeAppDataMapper(overTimeDTO);
        return result >= 1 ? 1 : 0;
    }
}
