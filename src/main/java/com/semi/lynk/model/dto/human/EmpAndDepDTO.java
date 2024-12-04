package com.semi.lynk.model.dto.human;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmpAndDepDTO {

    private int id;
    private String pw;
    private String name;
    private String email;
    private String accountStatus;
    private String failCount;
    private String picture;
    private DepartmentDTO departmentDTO;
}
