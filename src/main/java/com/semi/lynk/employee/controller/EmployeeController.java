package com.semi.lynk.employee.controller;

import com.semi.lynk.model.dto.EmpAndDepDTO;
import com.semi.lynk.model.dto.EmployeeDTO;
import com.semi.lynk.model.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

        return "lists";
    }

    @GetMapping("join")
    public ModelAndView employeeJoin(ModelAndView mv){

        List<EmpAndDepDTO> joinList = employeeService.showJoinResult();

        mv.addObject("joinList" , joinList);
        mv.setViewName("joinResult");
        return mv;
    }
}
