package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegisterDTO {

    private String name;                // 이름
    private String depName;             // 소속
    private int id;                     // 사번
    private String position;            // 직책
    private String phoneNumber;         // 대표(휴대) 전화
    private String extensionNumber;     // 내선 번호
    private String address;             // 주소
    private String email;               // 이메일
}
