package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="employee_salary")
public class EmployeeSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    @Column(name = "eid")
    @NotNull(message = "eid must not be null!")
    private  int eid;

    @Column(name = "total_salary")
    @NotNull(message = "Total must not be null!")
    private  int totalSalary;
}
