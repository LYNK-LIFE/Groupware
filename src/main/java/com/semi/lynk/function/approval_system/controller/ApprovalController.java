package com.semi.lynk.function.approval_system.controller;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.service.ApprovalService;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @GetMapping("/credraft")
    public String creDraft(Model model) {
        model.addAttribute("draftDTO", new DraftDTO());
        return "function/approval_system/createdraft";
    }

    @PostMapping("/credraft")
    public String createDraft(@ModelAttribute("draftDTO") DraftDTO draftDTO, HttpSession session) {
        String empNo = (String) session.getAttribute("empNo");
        draftDTO.setEmployeeNo(empNo);
        draftDTO.setDraftCurrentStep(1);
        draftDTO.setDraftState(0);
        draftDTO.setDraftDate(LocalDateTime.now());
        draftDTO.setDraftLastStep(9);
        approvalService.createDraft(draftDTO);
        return "redirect:/";
    }



//    @GetMapping("/curdraft")
//    public String curDraft(Model model) {
//        return "function/approval_system/currentdraft";
//    }
//
//    @GetMapping("/findraft")
//    public String finDraft(Model model) {
//        return "function/approval_system/finishdraft";
//    }
//
//    @GetMapping("/dindraft")
//    public String dinDraft(Model model) {
//        return "function/approval_system/dindraft";
//    }
//
//    @GetMapping("/doapproval")
//    public String doapproval(Model model) {
//        return "function/approval_system/doapproval";
//    }
//
//    @GetMapping("/finapproval")
//    public String finapproval(Model model) {
//        return "function/approval_system/finapproval";
//    }
}
