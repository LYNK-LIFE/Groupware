package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dto.ApprovalList;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApprovalService {
    void createApproval(ApprovalList approvalList);

    Long createDraft(DraftDTO draftDTO);

    Page<DraftDTO> getDraftsPaged(String empNo, String state, int page, int size, String keyword);

    DraftDTO getDraftByDNO(Long draftNo);

    List<EmployeeDTO> getAllEmployees();
}
