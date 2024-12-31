package com.semi.lynk.function.db_management.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InquiryDTO {
    private int contractMngNo; // 계약일련번호
    private  String customerName; // 고객명
    private String insuredName; // 피보험자
    private int productCategory; // 보험회사명 코드
    private String productName; // 상품
    private String contractNo; // 계약번호
    private String employeeName; // 설계사 이름
}
