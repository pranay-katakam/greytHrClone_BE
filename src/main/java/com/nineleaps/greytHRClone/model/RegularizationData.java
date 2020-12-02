package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "regularization_data")
public class RegularizationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reguId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

    @Column(name="regularized_on")
    private LocalDateTime regularizedOn;

    private LocalDate date;


    @Column(name="first_in")
    private LocalDateTime firstIn;

    @Column(name="last_out")
    private LocalDateTime lastOut;

    private LeaveStatus status;

    @Column(name = "regularized_by")
    private String regularizedBy;

    @Column(name = "remarks")
    private String remarks;
}
