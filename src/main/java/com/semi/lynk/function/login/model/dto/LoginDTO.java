package com.semi.lynk.function.login.model.dto;

import com.semi.lynk.common.UserRole;
import com.semi.lynk.function.human.model.dto.DepartmentDTO;
import com.semi.lynk.function.human.model.dto.HumanDTO;
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
    private String image;
    private UserRole role;
    private int empStatus;
    private int loginFailCount;
    private int workingStatus;
    private String depName; // 부서이름
    private String position;            // 직책



//    public List<String> getRole() {
//        if (this.role.getRole().length() > 0) {
//            return Arrays.asList(this.role.getRole().split(","));
//        }
//        return new ArrayList<>();
//    }

}
