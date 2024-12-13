package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class LookUpDTO {

    private int id;
    private String name;
    private HumanDTO humanDTO;
}
