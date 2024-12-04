package com.semi.lynk.model.dto.human;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeDTO {

    private int id;             // 사번
    private String pw;          // 패스워드
    private String name;        // 이름
    private String email;       // 이메일
    private String accountStatus; // 계정 상태
    private String failCount;       // 로그인 실패 횟수
    private String picture;         // 사진

}
