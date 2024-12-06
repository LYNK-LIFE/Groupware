package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import com.semi.lynk.function.human.model.dto.EmpAndDepDTO;
import com.semi.lynk.function.human.model.dto.EmployeeDTO;
import com.semi.lynk.function.human.model.dto.HumanDTO;
import com.semi.lynk.function.human.model.dto.RegistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeService {

    private EmployeeMapper mapper;

    @Autowired
    public EmployeeService (EmployeeMapper mapper) {
        this.mapper = mapper;
    }

    public List<EmployeeDTO> employeeList () {

        return mapper.employeeFullList();
    }

    public List<EmpAndDepDTO> joinList() {

        return mapper.joinListResult();
    }

    // DML용
    @Transactional // 정상 처리 : commit , 예외 발생 : rollback
    public Map<String, Integer> humanResister(RegistDTO registDTO) {

//        int id = registDTO.getId();
//        String name = registDTO.getName();
//        String address = registDTO.getAddress();
//        String email = registDTO.getEmail();
//        String position = registDTO.getPosition();
//        String phoneNumber = registDTO.getPhoneNumber();
//        String picture = registDTO.getPicture();
////        String extensionNumber = registDTO.getExtensionNumber();
////        String depName = registDTO.getDepName();
//
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setId(id);
//        employeeDTO.setName(name);
//        employeeDTO.setEmail(email);
//        employeeDTO.setPicture(picture);
//
//        HumanDTO humanDTO = new HumanDTO();
//        humanDTO.setAddress(address);
//        humanDTO.setPosition(position);
//        humanDTO.setPhoneNumber(phoneNumber);

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id(registDTO.getId())
                .name(registDTO.getName())
                .email(registDTO.getEmail())
                .picture(registDTO.getPicture())
                .build();

        HumanDTO humanDTO = HumanDTO.builder()
                .position(registDTO.getPosition())
                .phoneNumber(registDTO.getPhoneNumber())
                .address(registDTO.getAddress())
                .build();

//        logic(employeeDTO , humanDTO);

        Map <String, Object> map1 = new HashMap<>();
        Map <String, Object> map2 = new HashMap<>();

        map1.put("data1" , employeeDTO);
        map2.put("data2", humanDTO);

        int result1 = mapper.registEmployee(map1);
        int result2 = mapper.registHuman(map2);

        Map<String , Integer> result = new HashMap<>();
        result.put("firstResult" , result1);
        result.put("secondResult" , result2);

        return result;
    }
//    public void logic (EmployeeDTO emp , HumanDTO human) {
//        emp.getId();
//        emp.getName();
//        emp.getEmail();
//        emp.getPicture();
//
//        human.getAddress();
//        human.getPosition();
//        human.getPhoneNumber();
//    }

}
