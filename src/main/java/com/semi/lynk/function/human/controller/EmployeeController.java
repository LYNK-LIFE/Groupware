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
        mv.setViewName("function/human/registPage");
        return mv;
    }

    @GetMapping("regist")
    public String moveRegistPage () {
        return "function/human/registPage";
    }


    @PostMapping ("regist")
    public String humanRegist (@ModelAttribute RegistHumDTO registHumDTO
                               ,RedirectAttributes rtt
                                ,Locale locale) {


        System.out.println("Human DTO: " + registHumDTO);
        int result = employeeService.humanRegist(registHumDTO);

        if (result == 1) {
            rtt.addFlashAttribute("successMessage"
                    ,messageSource.getMessage("registSuccess",new Object[]{} , locale));
            return "redirect:/employee/regist";
            // redirect는 url이 동작 , view 페이지 입력이 아니라, 핸들러 메서드를 지정해야 함!!
            // 클릭 시  @GetMapping("regist")
            //    public String moveRegistPage () {
            //        return "function/human/registPage";
            //    }가 동작하며 registPage로 옴!
        } else {
            return "redirect:/employee/main";
        }
    }
}
