package com.semi.lynk.function.login.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeAndHumanDTO {

    private String userID;
    private String userName;
    private String userPass;
    private String email;
    private String deptNo;
    private String position;
    private String etc;

}
