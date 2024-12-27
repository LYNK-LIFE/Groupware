package com.semi.lynk.function.approval_system.controller;

import com.semi.lynk.function.approval_system.model.dto.ApprovalDTO;
import com.semi.lynk.function.approval_system.model.dto.DraftDTO;
import com.semi.lynk.function.approval_system.model.dto.EmployeeDTO;
import com.semi.lynk.function.approval_system.service.ApprovalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/notice/list";
    }

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
        return "redirect:/approval/list/ondraft";
    }

    @GetMapping("/{draftNo}")
    public String viewDraft(@PathVariable("draftNo") Long draftNo, Model model) {

        DraftDTO draft = approvalService.getDraftByDNO(draftNo);

        model.addAttribute("draft", draft);

        return "function/approval_system/view";
    }



    @GetMapping("/list/{action}")
    public String list(Model model, HttpSession session,
                          @PathVariable String action,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "12") int size) {
        String state = "draft_state < 2";
        switch (action) {
            case "findraft" : state = "draft_state = 2"; break;
            case "dindraft" : state = "draft_state = 9"; break;
        }

        String empNo = (String) session.getAttribute("empNo");
        Page<DraftDTO> draftPage = approvalService.getDraftsPaged(empNo, state, page, size);

        model.addAttribute("drafts", draftPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", draftPage.getTotalPages());
        model.addAttribute("totalItems", draftPage.getTotalElements());
        model.addAttribute("action", action);
        return "function/approval_system/list";
    }

    @GetMapping("/{draftNo}/delete")
    public String deleteDraft(@PathVariable("draftNo") Long draftNo, Model model) {
        return "function/approval_system/view";
    }

    @GetMapping("/{draftNo}/edit")
    public String editDraft(@PathVariable("draftNo") Long draftNo, Model model) {
        return "function/approval_system/view";
    }
    @GetMapping("/addApproval")
    public String showAddApprovalForm(Model model) {
        List<EmployeeDTO> employees = approvalService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("approvalForm", new ApprovalDTO());
        System.out.println("여긴왔지?");
        return "function/approval_system/approval";
    }

    @PostMapping("/addApproval")
    public String addApproval(@ModelAttribute ApprovalDTO approvalDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "approval/addApproval";
        }
        approvalService.createApproval(approvalDTO);
        return "redirect:/approval/success";
    }

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
