package com.semi.lynk.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//ps 안녕하세요 반갑습니다.
public class MainController {

    @GetMapping("list")
    public String list (){
        return "/";
    }
}
