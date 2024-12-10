package com.semi.lynk.function.login.controller;

import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/function/login/*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("empAdd")
    public void empAddPage() {}

    @PostMapping("empAdd")
    public ModelAndView empAddPage(@ModelAttribute EmpAddDTO empAddDTO, ModelAndView mv) {

        System.out.println("empAddDTO = " + empAddDTO);
        
        Integer result = loginService.addEmployee(empAddDTO);
        String message = null;

        if (result == null) {
            message = "중복 된 회원이 존재합니다.";
        } else if (result == 0) {
            message = "서버 내부에서 오류가 발생했습니다.";
            mv.setViewName("function/login/empAdd");
        } else if (result >= 1) {
            message = "회원 가입이 완료되었습니다.";
            mv.setViewName("function/login/login");
        }

        mv.addObject("message", message);

        return mv;

    }

}
