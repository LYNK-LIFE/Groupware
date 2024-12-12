package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class EmployeeDTO {

    private int id;             // 사번
    private String name;        // 이름
    private HumanDTO humanDTO;  // 직책 , 고용 구분 , 전화 번호 , ssn
}
