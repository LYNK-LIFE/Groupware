package com.semi.lynk.function.management.model.dao;

import com.semi.lynk.function.management.model.dto.AccountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManagedMapper {

    //활성화 계정
    Map<String, Object> getMemberStatusCounts();
    List<Map<String, Object>> getActiveEmployee();

    //활성화 계정 삭제
    void deactivateAccounts(@Param("empIDs") List<String> empIDs);

    //비활성화 계정
    Map<String, Object> getMemberStatusCountsInac();
    List<Map<String, Object>> getInactiveEmployee();

    //계정 편집
    AccountDTO getAccountByEmpID(String empID);
}
