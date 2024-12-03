package com.semi.lynk.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("list")
    public String list (){
        return "/";
    }
}
