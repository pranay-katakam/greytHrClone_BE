package com.nineleaps.greytHRClone.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


@Data
@Entity
@Table(name="employee_data")
public class EmployeeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "manager_id")
    private int managerId=0;

    @Column(name = "dob")
//    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dob;

    @Column(name = "location")
    private String location;

    @Column(name = "created_date")
    private Timestamp createdDate = new Timestamp(System.currentTimeMillis());


    @Column(name = "department")
    private String department;

    @Column(name = "designation")
    private String designation;


}

