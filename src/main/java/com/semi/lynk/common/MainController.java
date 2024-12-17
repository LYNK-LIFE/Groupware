package com.semi.lynk.common;

import com.semi.lynk.function.login.model.EmpDetails;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.service.LoginService;
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
    public ModelAndView mainPage(@AuthenticationPrincipal EmpDetails empDetails, ModelAndView mv) {
        // 필요한 정보 가져오기
        String empNo = empDetails.getUsername();        // 로그인 ID
        String empName = empDetails.getName();          // 이름
//        String deptName = empDetails.getDeptName();     // 부서
//        String position = empDetails.getPosition();     // 사번

        // 모델에 추가
        mv.addObject("user", empDetails);
        System.out.println("userNo = " + empNo);
        System.out.println("userName = " + empName);
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
