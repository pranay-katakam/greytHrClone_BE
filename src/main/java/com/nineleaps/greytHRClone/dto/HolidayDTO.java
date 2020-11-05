package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDTO {

    private Date holidayDate;
    private String holidayEvent;
}
