package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.model.calendar.OverTimeApplicationDTO;
import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarDTO> showCalendar();

    List<CalendarDTO> showMyAppStatus();

    List<VacationApplicationDTO> vacationAppMapper();

    int vacAppUpdateMapper(VacationApplicationDTO vacationApplicationDTO);

    int vacAppInsertMapper1(VacationApplicationDTO vacationApplicationDTO);

    int vacAppInsertMapper2(VacationApplicationDTO vacationApplicationDTO);

    List<OverTimeApplicationDTO> overTimeAppMapper();

    int overTimeAppDataMapper(OverTimeApplicationDTO overTimeDTO);
}
