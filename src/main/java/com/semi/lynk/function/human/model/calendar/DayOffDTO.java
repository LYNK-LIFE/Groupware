package com.semi.lynk.function.human.model.calendar;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DayOffDTO {

    private int id;                     // 사번
    private Date leaveDate;             // 휴가 일자
    private int leaveType;            // 휴가 타입 (0 / 1 반차 / 2 연차)
    private String leaveMemo;           // 메모
}
