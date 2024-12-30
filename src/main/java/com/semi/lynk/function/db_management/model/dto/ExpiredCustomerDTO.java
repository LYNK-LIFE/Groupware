package com.semi.lynk.function.db_management.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpiredCustomerDTO {
    private int insuranceCompanyCode; // 보험회사 코드
    private String insuranceCompanyName; // 보험회사 이름
    private String productName; // 상품명
    private String customerName; // 고객명
    private LocalDate expiringDate; // 만기일자
}
