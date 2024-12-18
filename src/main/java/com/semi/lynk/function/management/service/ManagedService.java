package com.semi.lynk.function.management.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import com.semi.lynk.function.management.model.dao.ManagedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagedService {

    //****************************************************************
    // 관리자 페이지
    //****************************************************************

    @Autowired
    private ManagedMapper managedMapper;

    //****************************************************************
    // 활성화 계정 조회 페이지
    //****************************************************************

    // 활성화 계정 수 카운트
    public Map<String, Object> getMemberStatusCounts() {
        return managedMapper.getMemberStatusCounts();
    }

    // 활성화 계정 리스트
    public List<Map<String, Object>> getActiveEmployee() {
        return managedMapper.getActiveEmployee();
    }

    //****************************************************************

}
