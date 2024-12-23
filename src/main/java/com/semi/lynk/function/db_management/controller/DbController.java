package com.semi.lynk.function.db_management.controller;

import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import com.semi.lynk.function.db_management.service.DbService;
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


    @GetMapping("/metlife")
    public String metlifeSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/metlife";
    }

    @PostMapping("/metlife")
    public ResponseEntity<String> metlifeRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/metlife/products")
    @ResponseBody
    public List<ProductManageDTO> metlifeProducts() {return dbService.insuranceProducts();}


    @DeleteMapping("/metlife/{productNo}")
    public ResponseEntity<Void> metlifedeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }

//=====================================================================================================================


    @GetMapping("/hanwhalife")
    public String hanwhalifeSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/hanwhalife";
    }

    @PostMapping("/hanwhalife")
    public ResponseEntity<String> hanwhalifeRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/hanwhalife/products")
    @ResponseBody
    public List<ProductManageDTO> hanwhalifeProducts() {return dbService.insuranceProducts();}


    @DeleteMapping("/hanwhalife/{productNo}")
    public ResponseEntity<Void> hanwhalifedeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }


//    ==================================================================================================================

    @GetMapping("/shinhan")
    public String shinhanSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/shinhan";
    }

    @PostMapping("/shinhan")
    public ResponseEntity<String> shinhanRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/shinhan/products")
    @ResponseBody
    public List<ProductManageDTO> shinhanProducts() {return dbService.insuranceProducts();}


    @DeleteMapping("/shinhan/{productNo}")
    public ResponseEntity<Void> shinhandeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }

//====================================================================================================================


    @GetMapping("/heungkuk")
    public String heungkukSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/heungkuk";
    }

    @PostMapping("/heungkuk")
    public ResponseEntity<String> heungkukRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/heungkuk/products")
    @ResponseBody
    public List<ProductManageDTO> heungkukProducts() {return dbService.insuranceProducts();}


    @DeleteMapping("/heungkuk/{productNo}")
    public ResponseEntity<Void> heungkukdeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }


//======================================================================================================================

    @GetMapping("/lina")
    public String linaSelect(Model model) {
        model.addAttribute("productManageDTO", new ProductManageDTO());
        return "function/db_management/lina";
    }

    @PostMapping("/lina")
    public ResponseEntity<String> linaRegistration(@RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    @GetMapping("/lina/products")
    @ResponseBody
    public List<ProductManageDTO> linaProducts() {return dbService.insuranceProducts();}


    @DeleteMapping("/lina/{productNo}")
    public ResponseEntity<Void> linadeleteProduct(@PathVariable("productNo") String productNo) {
        dbService.deleteProduct(productNo);
        return ResponseEntity.ok().build();
    }

//======================================================================================================================


    @GetMapping("/contractregistration")
    public String Contractregistration () {
        return "function/db_management/contractregistration";
    }





}




