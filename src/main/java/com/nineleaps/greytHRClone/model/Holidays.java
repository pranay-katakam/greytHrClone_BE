package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "holidays")
public class Holidays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int holidayId;

    @Column(name = "holiday_date")
    private Date holidayDate;

    @Column(name = "holiday_event")
    private String holidayEvent;

}
