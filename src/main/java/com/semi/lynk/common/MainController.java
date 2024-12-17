package com.semi.lynk.common;

import com.semi.lynk.function.login.model.EmpDetails;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @Autowired
    private LoginService loginService;

//    @GetMapping("/")
//    public String list (){
//        return "common/main";
//    }

    @GetMapping("/")
    public ModelAndView mainPage(@AuthenticationPrincipal EmpDetails empDetails, HttpSession session, ModelAndView mv) {

        // 세션에 사용자 정보 저장
        session.setAttribute("empNo", empDetails.getUsername());
        session.setAttribute("empName", empDetails.getName());
        session.setAttribute("empDetails", empDetails);

        // 모델에 추가
        mv.addObject("user", empDetails);
        System.out.println("session = " + session.getAttribute("empName"));
        mv.setViewName("/common/main");
        return mv;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "function/login/login";
    }

    @GetMapping("/passwordRequest")
    public String passwordRequestPage(){
        return "function/login/passwordRequest";
    }

    @GetMapping("/passwordReset")
    public String passwordResetPage(){
        return "function/login/passwordReset";
    }

}
