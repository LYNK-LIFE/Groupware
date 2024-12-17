package com.semi.lynk.function.notice_board.service;

import com.semi.lynk.function.notice_board.model.dao.NoticeMapper;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public void createNotice(NoticeDTO noticeDTO) {
        noticeMapper.insertNotice(noticeDTO);
    }

    @Override
    public List<NoticeDTO> getAllNotices() {
        List<NoticeDTO> noticeDTOList = (List<NoticeDTO>) noticeMapper.getAllNotices();
        return noticeDTOList;
    }

    @Override
    public NoticeDTO getNoticeById(Long noticeNo) {
        return noticeMapper.selectNoticeById(noticeNo);
    }

    @Override
    public void updateNotice(NoticeDTO noticeDTO) {
        noticeMapper.updateNotice(noticeDTO);
    }

    @Override
    public void deleteNotice(Long noticeNo) {
        noticeMapper.deleteNotice(noticeNo);
    }

    @Override
    public void updateViewCnt(Long noticeNo) {
        System.out.println("여기 오니?");
        noticeMapper.updateViewCnt(noticeNo);
    }

}
