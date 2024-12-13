package com.semi.lynk.function.notice_board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NoticeController {
    @GetMapping("/notice")
    public String employeeList () { return "function/notice_board/notice"; }
}
