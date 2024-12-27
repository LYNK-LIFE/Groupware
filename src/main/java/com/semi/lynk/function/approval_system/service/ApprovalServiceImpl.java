package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dao.ApprovalMapper;
import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public List<EmployeeDTO> getAllEmployees()
    {
        List<EmployeeDTO> employees = approvalMapper.getAllEmployees();
        return employees;
    }

    @Override
    public void createApproval(ApprovalDTO approval){


    };

    @Override
    public void createDraft(DraftDTO draftDTO){
        System.out.println("draftDTO = " + draftDTO);approvalMapper.insertDraft(draftDTO);}

    @Override
    public Page<DraftDTO> getDraftsPaged(String empNo, String state, int page, int size){
        int count = approvalMapper.getDraftsCount(empNo, state);
        int start = page * size; // 해당페이지의 시작글번호
        List<DraftDTO> drafts = approvalMapper.getDrafts(empNo, state, start, Math.min(count, start+size));
        System.out.println("drafts = " + drafts);
        return new PageImpl<>(drafts, PageRequest.of(page, size), count);
    }

    @Override
    public DraftDTO getDraftByDNO(Long draftNo){
        return approvalMapper.selectDraftByDNO(draftNo);
    }
}
