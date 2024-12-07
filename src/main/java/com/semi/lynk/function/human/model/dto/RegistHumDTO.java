package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegistHumDTO {

    private long id;
    private String address;
    private String position;
    private String phoneNumber;
}
