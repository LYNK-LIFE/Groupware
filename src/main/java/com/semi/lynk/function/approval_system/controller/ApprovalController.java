package com.semi.lynk.function.approval_system.controller;

import com.semi.lynk.function.approval_system.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalservice;

    @GetMapping("/credraft")
    public String creDraft(Model model) {
        return "function/approval_system/creatdraft";
    }

    @GetMapping("/curdraft")
    public String curDraft(Model model) {
        return "function/approval_system/currentdraft";
    }

    @GetMapping("/findraft")
    public String finDraft(Model model) {
        return "function/approval_system/finishdraft";
    }

    @GetMapping("/dindraft")
    public String dinDraft(Model model) {
        return "function/approval_system/dindraft";
    }

    @GetMapping("/doapproval")
    public String doapproval(Model model) {
        return "function/approval_system/doapproval";
    }

    @GetMapping("/finapproval")
    public String finapproval(Model model) {
        return "function/approval_system/finapproval";
    }
}
