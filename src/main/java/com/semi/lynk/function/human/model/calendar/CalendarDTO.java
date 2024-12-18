package com.semi.lynk.function.human.model.calendar;

import com.semi.lynk.function.human.model.dto.EmployeeDTO;
import com.semi.lynk.function.human.model.dto.HumanDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CalendarDTO {
    private String id; // 유일 식별자 (UUID 또는 DB 식별자)
//    private String title; // 이벤트 제목
//    private String memo;  // 이벤트에 대한 설명
//    private String start; // 시작 날짜 (ISO 8601 형식)  ex) 2024-12-17T14:30:00+09:00
//    private String end;   // 종료 날짜 (ISO 8601 형식)  ex) 2024-12-17T14:30:00+09:00

    private EmployeeDTO employeeDTO; // 이름 불러와야돼서
    private HumanDTO humanDTO; // 연차 발생일수 / 사용 연차
    private CommuteDTO commuteDTO; // 출퇴근 이력 DTO
    private ScheduleDTO scheduleDTO; // 일정 DTO
}