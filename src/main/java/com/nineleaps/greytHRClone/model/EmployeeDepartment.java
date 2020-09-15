package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="employee_department")
public class EmployeeDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int depId;

    @Column(name = "department")
    private String department;
}
