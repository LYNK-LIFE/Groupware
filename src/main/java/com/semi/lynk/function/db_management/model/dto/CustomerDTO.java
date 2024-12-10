package com.semi.lynk.function.db_management.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {
    // 고객
    private  int customerNo; // 고객번호
    private  String customerName; // 고객명
    private  int customerSsn; // 주민번호
    private  int customerMobile; // 연락처
    private  String customerAddr; // 주소
    private  String customer_email; // 이메일

}
