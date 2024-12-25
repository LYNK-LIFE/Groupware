package com.semi.lynk.function.management.service;

import com.semi.lynk.function.management.model.dao.ManagedMapper;
import com.semi.lynk.function.management.model.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    // 계정 삭제
    public void deactivateAccounts(List<String> empIDs) {
        System.out.println("서비스단 empIDs = " + empIDs);
        if (empIDs == null || empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
        }
        managedMapper.deactivateAccounts(empIDs);
    }

    // 계정 복구
    public void restoreAccounts(List<String> empIDs) {
        if (empIDs == null || empIDs.isEmpty()) {
            System.out.println("empIDs가 비어 있습니다!");
        }
        managedMapper.restoreAccounts(empIDs);
    }

    //****************************************************************

    public AccountDTO getAccountByEmpID(String empID) {
        return managedMapper.getAccountByEmpID(empID);
    }

    public void updateAccount(String empID, String empName, int deptNo, String position, String email, String image) {
        managedMapper.updateAccount(empID, empName, deptNo, position, email, image);
    }

    // 계정 권한
    public List<Map<String, Object>> getActiveAccountRole() {
        return managedMapper.getActiveAccountRole();
    }

//    public void updateAccountRole(String empId, String roleDraft, String roleLeave, String roleDepartment, String roleNotice, String roleSchedule) {
//        Map<String, Object> roleData = new HashMap<>();
//        roleData.put("empID", empId);
//        roleData.put("roleDraft", roleDraft);
//        roleData.put("roleLeave", roleLeave);
//        roleData.put("roleDepartment", roleDepartment);
//        roleData.put("roleNotice", roleNotice);
//        roleData.put("roleSchedule", roleSchedule);
//
//        // Mapper 호출하여 DB 업데이트
//        managedMapper.updateAccountRole(roleData);
//    }
    public void updateRoles(List<Map<String, Object>> roles) {
        managedMapper.updateRoles(roles);
    }

    public void resetRoles(List<String> empIDs) {
        managedMapper.resetRoles(empIDs);
    }
}
