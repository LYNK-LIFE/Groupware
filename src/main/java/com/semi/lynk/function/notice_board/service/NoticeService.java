package com.semi.lynk.function.notice_board.service;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    void createNotice(NoticeDTO noticeDTO);
    List<NoticeDTO> getAllNotices();
    NoticeDTO getNoticeById(Long noticeNo);
    void updateNotice(NoticeDTO noticeDTO);
    void deleteNotice(Long noticeNo);
    void updateViewCnt(Long noticeNo);
}
