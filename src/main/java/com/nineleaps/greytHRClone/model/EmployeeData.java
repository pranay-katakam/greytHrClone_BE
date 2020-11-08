package com.nineleaps.greytHRClone.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.util.*;
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

    @Column(name = "image_name")
    private String imageName="defaultProfile.png";

    @Column(name = "manager_id")
    private int managerId=0;

    @Column(name = "dob")
//    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dob;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="location_id")
    private CompanyLocation location;

    @Enumerated
    private Gender gender;

    @Column(name = "contact_number")
    private long contactNumber;

    @Column(name = "created_date")
    private Timestamp createdDate = new Timestamp(System.currentTimeMillis());


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "dept_ids")
    private Set<EmployeeDepartment> departments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="desig_id")
    private EmployeeDesignation designation;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<Role> roles;


}

