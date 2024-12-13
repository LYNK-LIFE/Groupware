package com.semi.lynk.function.notice_board.controller;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;


@Controller
public class NoticeController {
    @GetMapping("/notice")
    public String noticeList() { return "list"; }

    @GetMapping("/notice_write")
    public String noticeWrite() { return "function/notice_board/write"; }

    @PostMapping("/notice_write")
    public String writeNotice(@ModelAttribute NoticeDTO noticeDTO,
                              @AuthenticationPrincipal UserDetails userDetails) {
        noticeDTO.setNoticeDate(LocalDateTime.now());
        noticeDTO.setNoticeHide(0);
        noticeDTO.setNoticeVote(0);
        noticeDTO.setViewerCount(0);
        noticeDTO.setEmployeeNo(userDetails.getUsername());

        // 여기서 noticeService를 통해 데이터베이스에 저장
        // noticeService.saveNotice(noticeDTO);

        return "redirect:/notice";
    }
}
