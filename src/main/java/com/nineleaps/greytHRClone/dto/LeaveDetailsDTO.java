package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDetailsDTO {

    private String type;
    private LocalDate posted;
    private LocalDate from;
    private LocalDate to;
    private int days;
    private LocalDate expired;

}
