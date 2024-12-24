package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import org.springframework.data.domain.Page;

public interface ApprovalService {
    void createApproval(ApprovalDTO approvalDTO);

    void createDraft(DraftDTO draftDTO);

    Page<DraftDTO> getDraftsPaged(String empNo, String state, int page, int size);
}
