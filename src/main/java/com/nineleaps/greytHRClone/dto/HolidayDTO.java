package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDTO {

    private LocalDate holidayDate;
    private String holidayEvent;
}
