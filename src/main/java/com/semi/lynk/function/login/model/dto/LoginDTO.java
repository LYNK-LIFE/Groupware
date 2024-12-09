package com.semi.lynk.function.login.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {

    private String empId;
    private String empPwd;
    private String role;

}
