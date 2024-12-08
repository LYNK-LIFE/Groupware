package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.human.model.dto.*;
import com.semi.lynk.function.human.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/employee/*")
public class EmployeeController {

    private EmployeeService employeeService;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);
    private final MessageSource messageSource;

    @Autowired
    public EmployeeController (EmployeeService employeeService
                                ,MessageSource messageSource) {
        this.employeeService = employeeService;
        this.messageSource = messageSource;
    }


    @GetMapping("list")
    public String employeeList (Model model) {

        List<EmployeeDTO> list = employeeService.employeeList();

        for (EmployeeDTO emp : list) {
            System.out.println(emp);
        }
        model.addAttribute("list", list);

        return "function/human/list";
    }

    @GetMapping("join")
    public ModelAndView joinList (ModelAndView mv) {

        List<EmpAndDepDTO> list = employeeService.joinList();
        for (EmpAndDepDTO emp : list) {
            System.out.println(emp);
        }

        mv.addObject("list" , list);
        mv.setViewName("function/human/joinList");
        return mv;
    }

    @GetMapping("regist")
    public String moveRegistPage () {
        return "function/human/registPage";
    }

//    @PostMapping ("regist") // 인사 등록 로직 처리 메서드
//    public String hunamRegist(@ModelAttribute RegistDTO registDTO, RedirectAttributes rtt, Locale locale){
//
//        System.out.println("컨트롤러 값 확인 registDTO" + registDTO);
//        Map<String , Integer> result = employeeService.humanResister(registDTO);
//
//        System.out.println("컨트롤러 값 확인 result" + result);
//        if (result.get("firstResult") >= 1 && result.get("secondResult") >= 1){
//            return "redirect:/function/human/registPage";
//        } else {
//            return "redirect:/function/human/list";
//        }
//    }

    @PostMapping ("regist")
    public String humanRegist (@ModelAttribute RegistEmpDTO registEmpDTO
                               , @ModelAttribute RegistHumDTO registHumDTO
                               ,RedirectAttributes rtt
                                ,Locale locale) {


        System.out.println("EmployeeNo: " + registEmpDTO.getId());
        System.out.println("Human DTO: " + registHumDTO);
        int result = employeeService.humanRegist(registEmpDTO, registHumDTO);

        if (result == 1) {
            rtt.addFlashAttribute("successMessage"
            ,messageSource.getMessage("registSuccess",new Object[]{registEmpDTO.getId()} , locale));
            return "function/human/registPage";

        } else {
            return "common/main";
        }
    }
}
