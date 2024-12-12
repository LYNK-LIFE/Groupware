package com.semi.lynk.function.login.model.dto;

import com.semi.lynk.common.UserRole;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private int deptNo;
    private UserRole role;

//    public List<String> getRole() {
//        if (this.role.getRole().length() > 0) {
//            return Arrays.asList(this.role.getRole().split(","));
//        }
//        return new ArrayList<>();
//    }

}
