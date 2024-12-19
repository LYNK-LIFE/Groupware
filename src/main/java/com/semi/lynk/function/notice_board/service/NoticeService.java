package com.semi.lynk.function.notice_board.service;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {
    void createNotice(NoticeDTO noticeDTO);
    List<NoticeDTO> getAllNotices();
    NoticeDTO getNoticeById(Long noticeNo);
    void updateNotice(Long noticeNo);
    void deleteNotice(Long noticeNo);
    void updateViewCnt(Long noticeNo);

    Page<NoticeDTO> getAllNoticesPaged(int page, int size);
}
