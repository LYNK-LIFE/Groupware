package com.semi.lynk.function.approval_system.service;

import com.semi.lynk.function.approval_system.model.dao.ApprovalMapper;
import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.ApprovalList;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public void setDraftState(Long draftNo, int lastStep)
    {
        approvalMapper.setDraftStepAndState(draftNo, lastStep,0);
    };

    @Override
    public List<EmployeeDTO> getAllEmployees()
    {
        List<EmployeeDTO> employees = approvalMapper.getAllEmployees();
        System.out.println("employees = " + employees);
        return employees;
    }

    @Override
    public void createApproval(ApprovalList approvalList){
        List<ApprovalDTO> approvals = approvalList.getApprovals();
        System.out.println("여긴 서비스");
        int i=0;
        for (ApprovalDTO approval : approvals) {
            i+=approvalMapper.insertApproval(approval);
            System.out.println("서비스임다 approval = " + approval);
        }
    };

    @Override
    public Long createDraft(DraftDTO draftDTO){
        approvalMapper.insertDraft(draftDTO);
        System.out.println("draftDTO.getDraftNo() = " + draftDTO.getDraftNo());
        return draftDTO.getDraftNo();
    }

    @Override
    public Page<DraftDTO> getDraftsPaged(String empNo, String state, int page, int size, String keyword){
        // 여기서 state는 결재중, 결재완료, 반려의 상태에 따라 쿼리문이 변경됨
        int count = approvalMapper.getDraftsCount(empNo, state, keyword);    // 페이징을 하기위해 먼저 전체 갯수 받아옴
        int start = page * size; // 해당페이지의 시작글번호
        List<DraftDTO> drafts = approvalMapper.getDrafts(empNo, state, start, size, keyword);
        return new PageImpl<>(drafts, PageRequest.of(page, size), count);
    }

    @Override
    public Page<DraftDTO> getApprovalsPaged(String empNo, int page, int size){
        int count = approvalMapper.getApprovalsCount(empNo);    // 페이징을 하기위해 먼저 전체 갯수 받아옴
        int start = page * size; // 해당페이지의 시작글번호
        List<DraftDTO> approvals = approvalMapper.selectForApproval(empNo, start, size);
        return new PageImpl<>(approvals, PageRequest.of(page, size), count);
    }

    @Override
    public DraftDTO getDraftByDNO(Long draftNo){
        return approvalMapper.selectDraftByDNO(draftNo);
    }
}
