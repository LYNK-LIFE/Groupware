package com.semi.lynk.function.db_management.model.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TopSalesContractDTO {
    private int eachPayment; // 납입금액
    private  String productNo; // 상품번호 FK
    private int totalSales; // 총합계금액
    private String employeeName; // 이름
    private int departmentNo; // 부서번호
    private String departmentName; // 부서명
    private String productName; // 상품
}
