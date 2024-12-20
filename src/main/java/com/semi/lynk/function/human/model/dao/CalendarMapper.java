package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarDTO> showCalendar();

    List<CalendarDTO> showMyAppStatus();

    int vacationAppMapper(VacationApplicationDTO vacationApplicationDTO);
}
