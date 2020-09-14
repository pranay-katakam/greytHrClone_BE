package com.nineleaps.greytHRClone.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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
    @CreatedDate
    private Date createdDate;

    @Column(name = "department")
    private String department;

    @Column(name = "department")
    private String designation;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "employee_empdept",
//            joinColumns = {@JoinColumn(name = "employee_id")},
//            inverseJoinColumns = {@JoinColumn(name = "dept_id")})
//    private Set<EmployeeDepartment> departments = new HashSet<>();
//
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name="desig_id")
//    private EmployeeDesignation designation;


}
