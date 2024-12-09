package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DraftFileDTO {
    // 기안문서
    private int draftFileNo; //기안문서번호
    private String oringinName; //파일이름
    private String saveName; // 저장명
    private String size; // 파일크기
    private int deleteYn; //삭제여부
    private Date createDate; //생성일시
    private Date deleteDate; // 삭제일시
    private String fileDate; // 파일데이터


}
