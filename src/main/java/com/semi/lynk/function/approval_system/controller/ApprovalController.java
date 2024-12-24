package com.semi.lynk.function.approval_system.controller;

import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.service.ApprovalService;
import com.semi.lynk.function.notice_board.model.dto.NoticeDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @GetMapping("/credraft")
    public String creDraft(Model model) {
        model.addAttribute("draftDTO", new DraftDTO());
        return "function/approval_system/create";
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
        return "redirect:/approval/ondraft";
    }

    @GetMapping("/{draftNo}")
    public String viewDraft(@PathVariable("draftNo") Long draftNo, Model model) {

        DraftDTO draft = approvalService.getDraftByDNO(draftNo);

        model.addAttribute("draft", draft);

        return "function/approval_system/view";
    }



    @GetMapping("/ondraft")
    public String onDraft(Model model, HttpSession session,
                           @RequestParam(defaultValue = "draft_state < 2") String state,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "12") int size) {
        String empNo = (String) session.getAttribute("empNo");
        Page<DraftDTO> draftPage = approvalService.getDraftsPaged(empNo, state, page, size);

        model.addAttribute("drafts", draftPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", draftPage.getTotalPages());
        model.addAttribute("totalItems", draftPage.getTotalElements());
        return "function/approval_system/on_draft_list";
    }

    @GetMapping("/{draftNo}/delete")
    public String deleteDraft(@PathVariable("draftNo") Long draftNo, Model model) {
        return "function/approval_system/view";
    }

    @GetMapping("/{draftNo}/edit")
    public String editDraft(@PathVariable("draftNo") Long draftNo, Model model) {
        return "function/approval_system/view";
    }
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
