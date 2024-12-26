package com.semi.lynk.function.approval_system.model.dao;

import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    DraftDTO selectDraftByDNO(Long draftNo);

    void insertDraft(DraftDTO draftDTO);

    int getDraftsCount(String empno, String state, String keyword);

    List<DraftDTO> getDrafts(String empno, String state, int page, int count, String keyword);
}
