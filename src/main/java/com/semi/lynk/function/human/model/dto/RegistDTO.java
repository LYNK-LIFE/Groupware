package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegistDTO {

    private int id;             // 사번
    private String name;        // 이름
    private String depName;     // 소속
    private String email;       // 이메일
    private String address;     // 주소
    private String position;    // 직책
    private String phoneNumber; // 휴대 전화 (대표 전화)
    private String extensionNumber; // 내선 번호
    private String picture;         // 사진


}
