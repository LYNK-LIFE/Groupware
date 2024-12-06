package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.human.model.dto.EmpAndDepDTO;
import com.semi.lynk.function.human.model.dto.EmployeeDTO;
import com.semi.lynk.function.human.model.dto.HumanDTO;
import com.semi.lynk.function.human.model.dto.RegistDTO;
import com.semi.lynk.function.human.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger logger = LogManager.getLogger(MenuContainer.class);

    @Autowired
    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
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

    @PostMapping ("regist") // 인사 등록 로직 처리 메서드
    public String hunamRegist(@ModelAttribute RegistDTO registDTO, RedirectAttributes rtt, Locale locale){

        System.out.println("컨트롤러 값 확인 registDTO" + registDTO);
        Map<String , Integer> result = employeeService.humanResister(registDTO);

        System.out.println("컨트롤러 값 확인 result" + result);
        if (result.get("firstResult") >= 1 && result.get("secondResult") >= 1){

            return "redirect:/function/human/registPage";
        } else {
            return "redirect:/function/human/list";
        }


    }
}
