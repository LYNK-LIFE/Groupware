package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.dto.*;
import com.semi.lynk.function.human.model.event.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDTO> employeeFullList();

    List<EmpAndDepDTO> joinListResult();

    int registMapperhum(RegistHumDTO registHumDTO);

    List<LookUpDTO> lookUpMapper();

    int modifyEmployee(ModifyDTO modifyDTO);

    List<Event> calendarMapper();


}
