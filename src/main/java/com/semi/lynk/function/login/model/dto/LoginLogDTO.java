package com.semi.lynk.function.login.model.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginLogDTO {

    private String empNo;
    private Integer loginStatus;
    private Timestamp loginTime;

}
