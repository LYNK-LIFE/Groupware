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


@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public String listNotices(Model model) {
        model.addAttribute("notice", noticeService.getAllNotices());
        return "function/notice_board/list";
    }

    @GetMapping("/create")
    public String createNoticeForm(Model model) {
        model.addAttribute("notice", new NoticeDTO());
        return "function/notice_board/create";
    }

    @PostMapping("/create")
    public String createNotice(@ModelAttribute NoticeDTO noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return "redirect:/notice/list";
    }

    @GetMapping("/{id}")
    public String viewNotice(@PathVariable("id") Long noticeNo, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(noticeNo));
        return "function/notice_board/view";
    }

    @GetMapping("/{id}/edit")
    public String editNoticeForm(@PathVariable("id") Long noticeNo, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(noticeNo));
        return "function/notice_board/edit";
    }

    @PostMapping("/{id}/edit")
    public String editNotice(@PathVariable("id") Long noticeNo, @ModelAttribute NoticeDTO noticeDTO) {
        noticeDTO.setNoticeNo(noticeNo);
        noticeService.updateNotice(noticeDTO);
        return "redirect:/notice";
    }

    @PostMapping("/{id}/delete")
    public String deleteNotice(@PathVariable("id") Long noticeNo) {
        noticeService.deleteNotice(noticeNo);
        return "redirect:/notice";
    }
}