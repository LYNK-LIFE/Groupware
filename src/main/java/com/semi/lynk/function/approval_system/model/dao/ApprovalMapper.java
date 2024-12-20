package com.semi.lynk.function.approval_system.model.dao;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApprovalMapper {
    void insertDraft(DraftDTO draftDTO);
}
