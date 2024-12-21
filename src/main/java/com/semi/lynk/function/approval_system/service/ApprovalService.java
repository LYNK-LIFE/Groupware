package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;

public interface ApprovalService {
    void createApproval(ApprovalDTO approvalDTO);

    void createDraft(DraftDTO draftDTO);
}
