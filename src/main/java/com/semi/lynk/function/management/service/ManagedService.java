package com.semi.lynk.function.management.service;

import com.semi.lynk.function.human.model.dao.EmployeeMapper;
import com.semi.lynk.function.management.model.dao.ManagedMapper;
import com.semi.lynk.function.management.model.dto.AccountDTO;
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
    // 활성화 / 비활성화 계정 조회 페이지
    //****************************************************************

    // 활성화 계정 수 카운트
    public Map<String, Object> getMemberStatusCounts() {
        return managedMapper.getMemberStatusCounts();
    }

    // 비활성화 계정 수 카운트
    public Map<String, Object> getMemberStatusCountsInac() {
        return managedMapper.getMemberStatusCountsInac();
    }

    // 활성화 계정 리스트
    public List<Map<String, Object>> getActiveEmployee() {
        return managedMapper.getActiveEmployee();
    }

    // 비활성화 계정 리스트
    public List<Map<String, Object>> getInactiveEmployee() {
        return managedMapper.getInactiveEmployee();
    }

    public void deactivateAccounts(List<String> empIDs) {
        System.out.println("서비스단 empIDs = " + empIDs);
        if (empIDs == null || empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
        }
        managedMapper.deactivateAccounts(empIDs);
    }

    //****************************************************************

    public AccountDTO getAccountByEmpID(String empID) {
        return managedMapper.getAccountByEmpID(empID);
    }

    public void updateAccount(String empID, String empName, String deptName, String position, String email) {
    }
}
