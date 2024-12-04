package com.semi.lynk.controller.human;

import com.semi.lynk.model.dto.human.EmpAndDepDTO;
import com.semi.lynk.model.dto.human.EmployeeDTO;
import com.semi.lynk.model.dto.human.HumanDTO;
import com.semi.lynk.service.human.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/employee/*")
public class EmployeeController {

    private EmployeeService employeeService;

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

        return "human/list";
    }

    @GetMapping("join")
    public ModelAndView joinList (ModelAndView mv) {

        List<EmpAndDepDTO> list = employeeService.joinList();
        for (EmpAndDepDTO emp : list) {
            System.out.println(emp);
        }

        mv.addObject("list" , list);
        mv.setViewName("human/joinList");
        return mv;
    }

    @GetMapping("regist")
    public String moveRegistPage () {
        return "human/registPage";
    }

    @PostMapping ("regist") // 인사 등록 로직 처리 메서드
    public String hunamRegist(@ModelAttribute HumanDTO humanDTO, RedirectAttributes rtt, Locale locale){

        employeeService.humanResister(humanDTO);

        return "human/registPage";
    }
}
