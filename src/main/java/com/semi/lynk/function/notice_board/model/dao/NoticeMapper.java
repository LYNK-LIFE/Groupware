package com.semi.lynk.function.notice_board.model.dao;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insertNotice(NoticeDTO noticeDTO);
    List<NoticeDTO> getAllNotices();
    NoticeDTO selectNoticeById(Long noticeNo);
    void updateNotice(Long noticeNo);
    void deleteNotice(Long noticeNo);
    void updateViewCnt(Long noticeNo);
}
