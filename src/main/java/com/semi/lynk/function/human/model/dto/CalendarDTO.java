package com.semi.lynk.function.human.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CalendarDTO {
    private String id; // 유일 식별자 (UUID 또는 DB 식별자)
    private String title; // 이벤트 제목
    private String start; // 시작 날짜 (ISO 8601 형식)
    private String end;   // 종료 날짜 (ISO 8601 형식)
}
