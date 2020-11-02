package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@Table(name = "holidays")
public class Holidays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int holidayId;

    @NotNull
    @Column(name = "holiday_date")
    private Date holidayDate;

    @NotNull
    @NotEmpty
    @Column(name = "holiday_event")
    private String holidayEvent;


}
