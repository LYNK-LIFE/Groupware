package com.semi.lynk.function.notice_board.service;

import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public void saveNotice(NoticeDTO noticeDTO) {
        Notice notice = convertToEntity(noticeDTO);
        noticeRepository.save(notice);
    }

    private Notice convertToEntity(NoticeDTO noticeDTO) {
        // DTO를 엔티티로 변환하는 로직
        // ...
    }
}
