package com.semi.lynk.function.login.controller;

import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/login/*")
public class LoginController {

    //****************************************************************
    // 회원가입 페이지 먼저 구성...
    //****************************************************************
    @Autowired
    private LoginService loginService;

    @GetMapping("empAdd")
    public String empAddPage() {
        System.out.println("getmapping은 되니?");
        return "function/login/empAdd";
    }

    @PostMapping("empAdd")
    public ModelAndView empAddPage(@ModelAttribute EmpAddDTO empAddDTO, ModelAndView mv) {

        System.out.println("empAddDTO = " + empAddDTO);     // 확인용
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

    //****************************************************************
    // 로그인
    //****************************************************************

    @GetMapping("login")
    public void loginPage() {}

    @GetMapping("failLogin")
    public ModelAndView loginFail(@RequestParam String message, ModelAndView mv) {

        // 실패시 핸들러에서 쿼리스트링으로 보내주는 errorMessage
        mv.addObject("message", message);
        mv.setViewName("failLogin");
        return mv;
    }

}
