package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.model.calendar.OverTimeApplicationDTO;
import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarDTO> showCalendarSelect();

    List<CalendarDTO> showMyAppStatus1();

    List<CalendarDTO> showMyAppStatus2();

    List<VacationApplicationDTO> vacationAppMapper();

    int vacAppUpdateMapper(VacationApplicationDTO vacationApplicationDTO);

    List<OverTimeApplicationDTO> overTimeAppMapper(); // 연장근무 신청 버튼

    int overTimeAppDataMapper(OverTimeApplicationDTO overTimeDTO);

    int vacAppInsertMapper1(VacationApplicationDTO vacationApplicationDTO);

    int vacAppInsertMapper2(VacationApplicationDTO vacationApplicationDTO);

    // draft 1씩 늘려주기 위한 selet
    int vacAppDayOffCount(VacationApplicationDTO vacationApplicationDTO);



}
