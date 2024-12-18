package com.semi.lynk.function.human.model.calendar;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ScheduleDTO { // 일정 DTO

    private int id; // 사번
    private int scheduleBound; // 일정 범위
    private LocalDateTime scheduleDate; // 일시
    private int scheduleType; // 일정 구분
    private String scheduleNote; // 내용
}
