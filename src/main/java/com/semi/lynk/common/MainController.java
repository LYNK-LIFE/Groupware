package com.semi.lynk.common;

import com.semi.lynk.function.login.model.dto.LoginDTO;
import com.semi.lynk.function.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public ModelAndView mainPage(ModelAndView mv) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            LoginDTO user = loginService.getLoginUsername(username);
            mv.addObject("user", user);
        }
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
