package com.semi.lynk.function.human.model.calendar;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VacationApplicationDTO {

    private int id;
    private float totalLeave;       // 총 연차 발생 일수
    private float usedLeave;        // 사용 연차
    private int leaveType;          // 휴가 타입
    private Date leaveDate;         // 휴가 일자

    private LocalDateTime scheduleDate; // 일시
    private int scheduleBound;
}
