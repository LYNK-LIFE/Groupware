package com.semi.lynk.function.login.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {

    private String empNo;
    private String empPwd;
    private String empName;
    private String email;
    private int empStatus;
    private int loginFailCount;
    private int workingStatus;
    private int deptNo;
    private String role;

}
