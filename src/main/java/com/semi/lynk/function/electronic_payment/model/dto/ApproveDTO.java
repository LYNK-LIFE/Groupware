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
    private  int draftNo;//기안번호 fk(복합식별자)
    private  int employeeNo; // 사번 fk(복합식별자)
}
