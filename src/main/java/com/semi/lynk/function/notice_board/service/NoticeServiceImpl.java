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
        List<NoticeDTO> allNotices = noticeMapper.getAllNotices(); // 비효율적이다... 창피함

        // 고정글을 앞에 넣는 작업
        // count(notice_hide==2)가 12 초과이면 
        // offset으로 12개씩 받아오게 한다면, notice_hide == 2인 목록의 전체 카운트를 받아와서 12 이하면 출력 후
        // notic_hide == 0인 글을 12-count(notice_hide==2)갯수만큼 출력
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
        int end = Math.min((start + size), allNotices.size()); // allnotice의 사이즈를 측정하지 말고, 쿼리의
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
        // 무식하게 다 받아와서 조건이 없음. 추후 페이지 수를 통해 몇번째 글부터 받아오면 되는지 넘겨서 12개씩 받아오게 할 것.
        return noticeDTOList;
    }

    @Override
    public NoticeDTO getNoticeById(Long noticeNo) {
        return noticeMapper.selectNoticeById(noticeNo);
    }

    @Override
    public void updateNotice(Long noticeNo) {
        noticeMapper.updateNotice(noticeNo);
    }

    @Override
    public void deleteNotice(Long noticeNo) {
        noticeMapper.deleteNotice(noticeNo);
    }

    @Override
    public void updateViewCnt(Long noticeNo) {
        noticeMapper.updateViewCnt(noticeNo);
    }

}
