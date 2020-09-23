package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "employee_salary")
public class EmployeeSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int salaryId;

    @Column(name = "emp_id")
    @NotNull(message = "ID must not be null!")
    private int empId;

    @Column(name = "salary")
    private int salary;
}
