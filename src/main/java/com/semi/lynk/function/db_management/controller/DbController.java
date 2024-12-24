package com.semi.lynk.function.db_management.controller;

import com.semi.lynk.function.db_management.model.dto.CustomerDTO;
import com.semi.lynk.function.db_management.model.dto.EmployeeDTO;
import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import com.semi.lynk.function.db_management.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation .*;

import java.util.List;


@Controller
    @RequestMapping("/db")
    public class DbController {

        private final DbService dbService;


        @Autowired
        public DbController(DbService dbService) {
            this.dbService = dbService;
        }


        //    상품등록 구성 (손해보험사,생명회사)
        @GetMapping("/list")
        public String InsuranceCompany() {
            return "function/db_management/list";
        }

//========================================================================================================================

        // 공통 페이지 로드
        @GetMapping("/{company}")
        public String loadCompanyPage(@PathVariable String company, Model model) {
            model.addAttribute("productManageDTO", new ProductManageDTO());
            return "function/db_management/" + company;
        }

        // 공통 상품 등록
        @PostMapping("/{company}")
        public ResponseEntity<String> registerProduct(@RequestBody ProductManageDTO productManageDTO) {
            dbService.insuranceRegistration(productManageDTO);
            return ResponseEntity.ok("Product registered successfully");
        }

        // 공통 상품 조회
        @GetMapping("/{company}/products")
        @ResponseBody
        public List<ProductManageDTO> getProducts() {
            return dbService.insuranceProducts();
        }

        // 공통 상품 삭제
        @DeleteMapping("/{company}/{productNo}")
        public ResponseEntity<Void> deleteProduct(@PathVariable String productNo) {
            dbService.deleteProduct(productNo);
            return ResponseEntity.ok().build();
        }

//====================================================================================================================

        // 고객 등록 페이지
        @GetMapping("/customer")
        public String customerSelect(Model model) {
            model.addAttribute("CustomerDTO", new CustomerDTO());
            return "function/db_management/customer";
        }


        // 고객 등록 처리
        @PostMapping("/customer")
        @ResponseBody
        public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) {
            System.out.println("customerDTO = " + customerDTO);
            dbService.registerCustomer(customerDTO);
            return ResponseEntity.ok("고객이 성공적으로 등록되었습니다.");
        }


//==================    ===================================================================================================




    // 계약 등록 페이지 로드
    @GetMapping("/contract")
    public String loadContractPage(Model model) {
        List<EmployeeDTO> employees = dbService.getAllEmployees();
        System.out.println("Employees: " + employees); // 로그 출력
        model.addAttribute("employees", employees);
        return "function/db_management/contract";
    }



    // 설계사 목록 조회 (JSON 응답)
    @GetMapping("/employees")
    @ResponseBody
    public List<EmployeeDTO> getEmployees() {
        return dbService.getAllEmployees();
    }



}


