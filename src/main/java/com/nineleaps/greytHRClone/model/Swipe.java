package com.nineleaps.greytHRClone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "swipes")
public class Swipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int swipeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

    @Column(name = "created_date")
    private Date createdDate = new Date();


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="door_add_id")
    private DoorAddress doorAddress;


}
