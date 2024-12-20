package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApprovalService {
    void approve(ApprovalDTO approvalDTO);
}
