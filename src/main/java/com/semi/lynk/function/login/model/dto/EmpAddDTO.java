package com.semi.lynk.function.login.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmpAddDTO {

    private String userID;
    private String userName;
    private String userPass;
    private String email;
    private String phone;
    private String joinDate;
    private String leaveDate;
    private String deptNo;
    private String position;
    private String etc;

}
