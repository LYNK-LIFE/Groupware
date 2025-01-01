package com.semi.lynk.function.approval_system.model.dao;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    DraftDTO selectDraftByDNO(Long draftNo);

    void insertDraft(DraftDTO draftDTO);

    int insertApproval(ApprovalDTO approvalDTO);

    int getDraftsCount(String empno, String state, String keyword);

    List<EmployeeDTO> getAllEmployees();

    List<DraftDTO> getDrafts(String empno, String state, int page, int count, String keyword);

    void setDraftStepAndState(Long draftNo, int lastStep, int state);

    List<DraftDTO> selectForApproval(String empNo, int start, int size);

    int getApprovalsCount(String empNo);
}
