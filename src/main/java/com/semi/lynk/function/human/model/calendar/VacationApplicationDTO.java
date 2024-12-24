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

    private int id;                 // 담당자 사번
    private String name;            // 담당자 이름
    private float totalLeave;       // 총 연차 발생 일수
    private float usedLeave;        // 사용 연차
    private int leaveType;          // 휴가 타입
    private Date leaveDate;         // 휴가 일자

    private LocalDateTime scheduleDate; // 일시
    private int scheduleBound;

    //
    private Date startOverDay;          // 시작일
    private LocalDateTime startOverTime; // 시작 시간

    private Date endOverDay;            // 종료일
    private LocalDateTime endOverTime;  //종료 시간

    private int scheduleType;           // 스케쥴 타입
    private String scheduleNote;        // 스케쥴 메모 (사 유)

}
