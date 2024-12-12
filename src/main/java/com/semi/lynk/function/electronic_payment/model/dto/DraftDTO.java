package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DraftDTO {
    private int draftDate; // 작성일자
    private int draftStatus; // 결재상태
    private int draftNo; // 기안번호
    private String draftMemo; // 비고
}