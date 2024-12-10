package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegistHumDTO {

    private long id;            // 사번
    private String name;
    private String address;     // 주소
    private String position;    // 직책
    private String phoneNumber; // 대표 전화
    private String date;    // 입사 일자
    private String employeementStatus; // 고용 여부

}
