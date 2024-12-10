package com.semi.lynk.function.db_management.model.dto;

import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractDTO {
    // 계약
    private int contractMngNo; // 계약일련번호
    private String contractNo; // 계약번호
    private Date contractDate; // 계약일자
    private int contractDuration; // 납입기간
    private int eachPayment; // 납입금액
    private String basicPayWith; // 기본결제수단
    private int paymentTerm; //납입주기
    private int paymentDay; //납입예정일자
    private String insuredName; // 피보험자
    private int insuredSsn; // 피보험자 주민번호
    private  String otherMatters; // 기타사항
    private  String productNo; // 상품번호 FK
    private  int customerNo; //고객번호 FK
    private  int employeeNo; // 사번 FK
    private  Date lastReformDate; // 최종 수정일자
    private String lastInseminatee; //최종 수정자




}
