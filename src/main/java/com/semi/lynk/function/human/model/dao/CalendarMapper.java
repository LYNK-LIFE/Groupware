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

    List<VacationApplicationDTO> vacationAppMapper();

    List<OverTimeApplicationDTO> overTimeAppMapper(); // 연장근무 신청 버튼

    int overTimeAppDataMapper(OverTimeApplicationDTO overTimeDTO);

    int vacAppUpdateMapper(VacationApplicationDTO vacationApplicationDTO);

    int vacAppInsertMapper(VacationApplicationDTO vacationApplicationDTO);

    // draft 1씩 늘려주기 위한 selet
    int vacAppDayOffCount(VacationApplicationDTO vacationApplicationDTO);

    int vacAppUpdateMapper2(VacationApplicationDTO vacationApplicationDTO);

    int vacAppUpdateMapper3(VacationApplicationDTO vacationApplicationDTO);

    int vacStatusUpdateMapper(int draftNo, int newState);
}
