package com.semi.lynk.function.approval_system.model.dao;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApprovalMapper {
    void insertNotice(ApprovalDTO approvalDTO);
}
