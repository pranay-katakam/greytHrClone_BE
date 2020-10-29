package com.nineleaps.greytHRClone.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity

@Table(name = "employee_designation")

public class EmployeeDesignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int desId;

    @NotNull(message = "designation must not be null!")
    private  String designation;

}
