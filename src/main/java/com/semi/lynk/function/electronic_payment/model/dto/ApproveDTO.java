package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApproveDTO {
    //결재
    private int approver; // 결제/반려
    private  String authoMemo; //메모
}
