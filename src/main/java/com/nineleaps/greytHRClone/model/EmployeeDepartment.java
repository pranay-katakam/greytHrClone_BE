//package com.nineleaps.greytHRClone.model;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//@Entity
//@Data
//@Table(name="employee_department")
//public class EmployeeDepartment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int departmentId ;
//
//    @Column(name="department")
//    private String department;
//
//    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "departments" )
//    private Set<EmployeeData> employees=new HashSet<>();
//
//
//}
