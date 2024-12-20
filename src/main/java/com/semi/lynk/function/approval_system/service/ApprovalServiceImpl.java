package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dao.ApprovalMapper;
import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.notice_board.model.dao.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public void createApproval(ApprovalDTO approvalDTO){

    };

    @Override
    public void createDraft(DraftDTO draftDTO){
        System.out.println("draftDTO = " + draftDTO);approvalMapper.insertDraft(draftDTO);}
}
