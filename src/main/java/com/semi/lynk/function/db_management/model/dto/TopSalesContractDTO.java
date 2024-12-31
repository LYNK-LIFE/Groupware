package com.semi.lynk.function.db_management.model.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TopSalesContractDTO {
    private int totalSales; // 총합계금액
    private String employeeName; // 이름
}
