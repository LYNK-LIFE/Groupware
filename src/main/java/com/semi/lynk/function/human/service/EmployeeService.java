package com.semi.lynk.function.human.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import com.semi.lynk.function.human.model.dto.*;
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
//    @Transactional // 정상 처리 : commit , 예외 발생 : rollback
//    public Map<String, Integer> humanResister(RegistEmpDTO registDTO) {
//        System.out.println("서비스 값 확인 registDTO" + registDTO);
//
//        EmployeeDTO employeeDTO = EmployeeDTO.builder()
//                .id(registDTO.getId())
//                .name(registDTO.getName())
//                .email(registDTO.getEmail())
//                .picture(registDTO.getPicture())
//                .build();
//        System.out.println("employeeDTO" + employeeDTO);
//        HumanDTO humanDTO = HumanDTO.builder()
//                .position(registDTO.getPosition())
//                .phoneNumber(registDTO.getPhoneNumber())
//                .address(registDTO.getAddress())
//                .build();
//        System.out.println("humanDTO" + humanDTO);
//
////        logic(employeeDTO , humanDTO);
//
//        Map <String, Object> map1 = new HashMap<>();
//        Map <String, Object> map2 = new HashMap<>();
//
//        map1.put("data1" , employeeDTO);
//        map2.put("data2", humanDTO);
//
//        int result1 = mapper.registEmployee(map1);
//        int result2 = mapper.registHuman(map2);
//        System.out.println("서비스 값 확인 result1" + result1);
//        System.out.println("서비스 값 확인 result2" + result2);
//
//        Map<String , Integer> result = new HashMap<>();
//        result.put("firstResult" , result1);
//        result.put("secondResult" , result2);
//        System.out.println("서비스 값 확인 result" + result);
//        return result;
//    }

    @Transactional
    public int humanRegist (RegistEmpDTO registEmpDTO
                          , RegistHumDTO registHumDTO) {

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put("emp" , registEmpDTO);
        map2.put("hum" , registHumDTO);
        int result1 = mapper.registMapperEmp(map1);
        int result2 = mapper.registMapperhum(map2);

        return result1 >= 1 && result2 >= 1 ? 1 : 0;
//        return result2;
    }

}
