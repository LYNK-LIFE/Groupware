package com.semi.lynk.function.db_management.controller;

import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import com.semi.lynk.function.db_management.service.DbService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/db")
public class DbController {

    private final DbService dbService;


    @Autowired
    public DbController (DbService dbService){this.dbService=dbService;}


//    상품등록 구성 (손해보험사,생명회사)
    @GetMapping("/list")
    public String InsuranceCompany () {
        return "function/db_management/list";
    }


    @GetMapping("/meritz")
    public String meritzSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/meritz"; // HTML 파일 경로
    }



    //    메리츠화재 상품등록 구성
    @PostMapping("/meritz")
    public ResponseEntity<String> meritzRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.meritzRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }


    @GetMapping("/meritz/products")
    @ResponseBody
    public List<ProductManageDTO> MeritzProducts() {
        return dbService.MeritzProducts(); // SELECT 쿼리를 실행
    }
}

