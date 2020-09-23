package com.nineleaps.greytHRClone.model;


import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="employee_designation")
public class EmployeeDesignation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int desId;

    @Column(name = "designation")
    private  String designation;

}
