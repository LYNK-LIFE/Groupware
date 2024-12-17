package com.semi.lynk.function.notice_board.service;

import com.semi.lynk.function.notice_board.model.dao.NoticeMapper;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Page<NoticeDTO> getAllNoticesPaged(int page, int size) {
        List<NoticeDTO> allNotices = noticeMapper.getAllNotices();

        // Sort notices (pinned notices first)
        allNotices.sort((a, b) -> {
            if (a.getNoticeHide() == 2 && b.getNoticeHide() != 2) {
                return -1;
            } else if (a.getNoticeHide() != 2 && b.getNoticeHide() == 2) {
                return 1;
            }
            return 0;
        });

        // Perform manual pagination
        int start = page * size;
        int end = Math.min((start + size), allNotices.size());
        List<NoticeDTO> pagedNotices = allNotices.subList(start, end);

        return new PageImpl<>(pagedNotices, PageRequest.of(page, size), allNotices.size());
    }

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
