package com.semi.lynk.function.management.model.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountDTO {
    private String empID;
    private String empName;
    private String deptName;
    private String position;
    private String email;
    private Date loginTime;
    private int memberStatus;

    private String empPwd;
    private int deptNo;
}
