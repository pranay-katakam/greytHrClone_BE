package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "holidays")
public class Holidays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int holidayId;

    @NotNull
    @Column(name = "holiday_date")
    private LocalDate holidayDate;

    @NotEmpty
    @NotNull
    @Column(name = "holiday_event")
    private String holidayEvent;


}
