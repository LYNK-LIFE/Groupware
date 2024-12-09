package com.semi.lynk.function.login.model.dto;

import com.semi.lynk.common.UserRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginUserDTO {

    private int empId;
    private String empName;
    private String empPwd;
    private UserRole userRole;
}
