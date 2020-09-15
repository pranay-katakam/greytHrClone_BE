package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="employee_designation")
public class EmployeeDesignation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int desId;

    @Column(name = "designation")
    private  String designation;
}
