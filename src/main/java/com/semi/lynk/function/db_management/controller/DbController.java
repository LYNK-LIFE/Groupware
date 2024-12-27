package com.semi.lynk.function.db_management.controller;

import com.semi.lynk.function.db_management.model.dto.ContractDTO;
import com.semi.lynk.function.db_management.model.dto.CustomerDTO;
import com.semi.lynk.function.db_management.model.dto.EmployeeDTO;
import com.semi.lynk.function.db_management.model.dto.ProductManageDTO;
import com.semi.lynk.function.db_management.service.DbService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation .*;

import java.util.ArrayList;
import java.util.Date;
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
        // 고객 데이터 추가
        model.addAttribute("customers", dbService.getAllCustomers());

        // 직원 데이터 추가
        List<EmployeeDTO> employees = dbService.getAllEmployees();
        System.out.println("Employees: " + employees); // 로그 출력
        model.addAttribute("employees", employees);

        // `contract` 객체를 데이터베이스에서 가져오거나 새로운 객체로 초기화
        ContractDTO contract = dbService.getLatestContract();
        if (contract == null) {
            contract = new ContractDTO();
            contract.setLastReformDate(new Date()); // 기본 수정일 설정
            contract.setLastInseminatee("Unknown User"); // 기본 수정자 설정
        }
        model.addAttribute("contract", contract); // `contract`를 모델에 추가

        return "function/db_management/contract"; // 템플릿 경로
    }
// =====================================================================================================================

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


    @GetMapping("/products")
    @ResponseBody
    public List<ProductManageDTO> searchProducts(@RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false, defaultValue = "0")
                                                 Integer insuranceCode) {
        if (insuranceCode == 0) {
            return new ArrayList<>(); // 기본 동작 처리
        }
        return dbService.searchProducts(keyword, insuranceCode);
    }

//======================================================================================================================


    @PostMapping("/contract")
    public ResponseEntity<String> registerContract(@ModelAttribute ContractDTO contractDTO, HttpSession session) {
        // 세션에서 사용자 이름 가져오기
        String empNo = (String) session.getAttribute("empNo");

        contractDTO.setLastReformDate(new Date()); // 현재 시간을 최종 수정일로 설정
        contractDTO.setLastInseminatee(empNo);

        // 계약 저장
        System.out.println("컨트롤러 contractDTO = " + contractDTO);

        dbService.registerContract(contractDTO);

        return ResponseEntity.ok("계약이 성공적으로 등록되었습니다.");
    }




}






















