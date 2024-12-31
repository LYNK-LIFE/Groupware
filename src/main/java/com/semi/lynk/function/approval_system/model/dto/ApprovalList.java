package com.semi.lynk.function.approval_system.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalList {
    private List<ApprovalDTO> approvals;
}
