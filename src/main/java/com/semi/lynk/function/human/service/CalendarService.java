package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.calendar.OverTimeApplicationDTO;
import com.semi.lynk.function.human.model.calendar.VacationApplicationDTO;
import com.semi.lynk.function.human.model.dao.CalendarMapper;
import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarService {

    private final CalendarMapper calendarMapper;

    @Autowired
    public CalendarService (CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public List<CalendarDTO> calendarService () {
        return calendarMapper.showCalendarSelect();
    }

    public List<CalendarDTO> myAppStatusService() {
        List<CalendarDTO> appStatus = calendarMapper.showMyAppStatus1();
//        List<CalendarDTO> appStatus2 = calendarMapper.showMyAppStatus2();
//        List<CalendarDTO> appStatus = new ArrayList<>();
//        appStatus.addAll(appStatus1);
//        appStatus.addAll(appStatus2);
        return appStatus;
    }

    public List<VacationApplicationDTO> vacationStatus() {
        System.out.println("휴가 신청 했을 때 서비스 왔는지 =================================");
        return calendarMapper.vacationAppMapper();
    }

    @Transactional
    public int vacAppService(VacationApplicationDTO vacationApplicationDTO) {
        System.out.println("서비스 오는지=====================================");

        int select = calendarMapper.vacAppDayOffCount(vacationApplicationDTO);
        System.out.println("1 : " + select);
        int dayOff = select + 1;
        System.out.println("2 : " + dayOff);

        vacationApplicationDTO.setDraftNo(dayOff);

//        System.out.println("vacationApplicationDTO : " + vacationApplicationDTO);
        int result1 = calendarMapper.vacAppUpdateMapper(vacationApplicationDTO);
//        System.out.println("result1 : " + result1);
//        int result2 = calendarMapper.vacAppInsertMapper1(vacationApplicationDTO);
//        System.out.println("result2 : " + result2);

        vacationApplicationDTO.getDraftNo();
        int result2 = calendarMapper.vacAppInsertMapper(vacationApplicationDTO);
//        System.out.println("result3 : " + result2);

        int result3 = calendarMapper.vacAppUpdateMapper2(vacationApplicationDTO);
//        System.out.println("result3 : " + result3);

        int result4 = calendarMapper.vacAppUpdateMapper3(vacationApplicationDTO);
//        System.out.println("result4 : " + result4);

        return (result1 >= 1) && (result2 >= 1) && (result3 >= 1) && (result4 >= 1) ? 1 : 0;
    }

    public List<OverTimeApplicationDTO> overTimeAppService() {
        return calendarMapper.overTimeAppMapper();
    }

    @Transactional
    public int overTimeAppDataService(OverTimeApplicationDTO overTimeDTO) {
        int result = calendarMapper.overTimeAppDataMapper(overTimeDTO);
        return result >= 1 ? 1 : 0;
    }

    @Transactional
    public int updateDraftState(int draftNo, int newState) {
        int result = calendarMapper.vacStatusUpdateMapper(draftNo , newState);
        return result >= 1 ? 1 : 0;
    }
}
