package com.semi.lynk.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String list (){
        return "common/main";
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
