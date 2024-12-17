package com.semi.lynk.function.notice_board.controller;

import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import com.semi.lynk.function.notice_board.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public String listNotices(Model model) {
        List<NoticeDTO> notices = noticeService.getAllNotices();

        notices.sort((a, b) -> {
            if (a.getNoticeHide() == 2 && b.getNoticeHide() != 2) {
                return -1;
            } else if (a.getNoticeHide() != 2 && b.getNoticeHide() == 2) {
                return 1;
            }
            return 0;
        });

        model.addAttribute("notices", notices);
        return "function/notice_board/list";
    }

    @GetMapping("/create")
    public String createNoticeForm(Model model) {
        model.addAttribute("noticeDTO", new NoticeDTO());
        return "function/notice_board/create";
    }

    @PostMapping("/create")
    public String createNotice(@ModelAttribute("noticeDTO") NoticeDTO noticeDTO) {
        noticeDTO.setNoticeDate(LocalDateTime.now());
        noticeService.createNotice(noticeDTO);
        return "redirect:/notice/list";
    }

    @GetMapping("/{noticeNo}")
    public String viewNotice(@PathVariable("noticeNo") Long noticeNo, Model model) {
        noticeService.updateViewCnt(noticeNo);
        model.addAttribute("notice", noticeService.getNoticeById(noticeNo));
        return "function/notice_board/view";
    }

    @GetMapping("/{noticeNo}/edit")
    public String editNoticeForm(@PathVariable("noticeNo") Long noticeNo, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(noticeNo));
        return "function/notice_board/edit";
    }

    @PostMapping("/{noticeNo}/edit")
    public String editNotice(@PathVariable("noticeNo") Long noticeNo, @ModelAttribute NoticeDTO noticeDTO) {
        noticeDTO.setNoticeNo(noticeNo);
        noticeService.updateNotice(noticeDTO);
        return "redirect:/notice";
    }

    @PostMapping("/{noticeNo}/delete")
    public String deleteNotice(@PathVariable("noticeNo") Long noticeNo) {
        noticeService.deleteNotice(noticeNo);
        return "redirect:/notice";
    }
}