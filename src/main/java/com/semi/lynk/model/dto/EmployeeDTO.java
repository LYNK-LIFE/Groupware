package com.semi.lynk.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeDTO {

    private int id;
    private String pw;
    private String name;
    private String email;
    private String accountStatus;
    private String failCount;
    private String picture;

}
