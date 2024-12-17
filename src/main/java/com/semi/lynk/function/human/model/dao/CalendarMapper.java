package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.dto.CalendarDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarDTO> showCalendar(CalendarDTO calendarDTO);
}
