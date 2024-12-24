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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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


//=====================================================================================================================


    // 계약 등록 페이지 로드
        @GetMapping("/contract")
        public String loadContractPage(Model model) {
            model.addAttribute("customers", dbService.getAllCustomers());
            List<EmployeeDTO> employees = dbService.getAllEmployees();
            System.out.println("Employees: " + employees); // 로그 출력
            model.addAttribute("employees", employees);
            return "function/db_management/contract";
        }

    // 고객 데이터 조회 (JSON 반환)
        @GetMapping("/customers")
        @ResponseBody
        public List<CustomerDTO> getCustomers() {
            return dbService.getAllCustomers();
    }

    // 설계사 목록 조회 (JSON 응답)
        @GetMapping("/employees")
        @ResponseBody
        public List<EmployeeDTO> getEmployees() {
            return dbService.getAllEmployees();
        }

    // 보험회사명 코드 조회
        @GetMapping("/insuranceCodes")
        @ResponseBody
        public List<Map<String, Object>> getInsuranceCodes() {
            List<Map<String, Object>> insuranceCodes = new ArrayList<>();
            insuranceCodes.add(Map.of("code", 1, "name", "메리츠화재"));
            insuranceCodes.add(Map.of("code", 2, "name", "현대해상"));
            insuranceCodes.add(Map.of("code", 3, "name", "한화손해보험"));
            insuranceCodes.add(Map.of("code", 4, "name", "삼성화재"));
            insuranceCodes.add(Map.of("code", 5, "name", "DB손해보험"));
            insuranceCodes.add(Map.of("code", 31, "name", "MetLife"));
            insuranceCodes.add(Map.of("code", 32, "name", "한화생명"));
            insuranceCodes.add(Map.of("code", 33, "name", "SinhanLife"));
            insuranceCodes.add(Map.of("code", 34, "name", "흥국생명"));
            insuranceCodes.add(Map.of("code", 35, "name", "라이나생명"));
            return insuranceCodes;
        }


        // 상품 데이터 조회
        @GetMapping("/products")
        @ResponseBody
        public List<ProductManageDTO> get2Products() {
            return dbService.getAllProducts();
        }

        // 계약 저장 (상품명만 표시용)
        @PostMapping("/displayProduct")
        @ResponseBody
        public ResponseEntity<String> displayProduct(@RequestBody String productName) {
            System.out.println("선택된 상품명: " + productName);
            return ResponseEntity.ok("상품명이 선택되었습니다.");

         }







    }














