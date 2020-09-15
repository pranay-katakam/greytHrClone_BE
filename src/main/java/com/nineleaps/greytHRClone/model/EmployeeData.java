package com.nineleaps.greytHRClone.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.util.Date;



@Data
@Entity
@Table(name="employee_data")
public class EmployeeData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int empId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "manager_id")
    private int managerId;

    @Column(name = "dob")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dob;

    @Column(name = "location")
    private String location;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "department")
    private String department;

    @Column(name = "designation")
    private String designation;


}

