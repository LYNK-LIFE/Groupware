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

//========================================================================================================================

    @GetMapping("/meritz")
    public String meritzSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/meritz"; // HTML 파일 경로
    }



    //    메리츠화재 상품등록 구성
    @PostMapping("/meritz")
    public ResponseEntity<String> meritzRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/meritz/products")
    @ResponseBody
    public List<ProductManageDTO> MeritzProducts() {
        return dbService.insuranceProducts(); // SELECT 쿼리를 실행
    }



    @DeleteMapping("/meritz/{productNo}")
    public ResponseEntity<Void> meritzdeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }

//    ============================================================================================================

    @GetMapping("/hyundai")
    public String hyundaiSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/hyundai";
    }

    @PostMapping("/hyundai")
    public ResponseEntity<String> hyundaiRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/hyundai/products")
    @ResponseBody
    public List<ProductManageDTO> HyundaiProducts() {return dbService.insuranceProducts();}


    @DeleteMapping("/hyundai/{productNo}")
    public ResponseEntity<Void> hyundaideleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }

//  ==============================================================================================================

    @GetMapping("/hanwha")
    public String hanwhaSelect(Model model) {
    model.addAttribute("productManageDTO", new ProductManageDTO());
    return "function/db_management/hanwha";
    }

    @PostMapping("/hanwha")
    public ResponseEntity<String> hanwhaRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/hanwha/products")
    @ResponseBody
    public List<ProductManageDTO> hanwhaProducts() {return dbService.insuranceProducts();}

    @DeleteMapping("/hanwha/{productNo}")
    public ResponseEntity<Void> hanwhadeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }

//==================================================================================================================

    @GetMapping("/samsung")
    public String samsungSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/samsung";
    }

    @PostMapping("/samsung")
    public ResponseEntity<String> samsungRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/samsung/products")
    @ResponseBody
    public List<ProductManageDTO> samsungProducts() {return dbService.insuranceProducts();}

    @DeleteMapping("/samsung/{productNo}")
    public ResponseEntity<Void> samsungdeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }


//================================================================================================================


    @GetMapping("/dbins")
    public String dbinsSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/dbins";
    }

    @PostMapping("/dbins")
    public ResponseEntity<String> dbinsRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/dbins/products")
    @ResponseBody
    public List<ProductManageDTO> dbinsProducts() {return dbService.insuranceProducts();}


    @DeleteMapping("/dbins/{productNo}")
    public ResponseEntity<Void> dbinsdeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }


//====================================================================================================================








}




