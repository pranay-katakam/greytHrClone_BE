package com.nineleaps.greytHRClone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String name;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    private int numberOfYears;

    public enum EventType {
        BIRTHDAY, ANNIVERSARY
    }

}
