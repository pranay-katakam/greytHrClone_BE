//package com.nineleaps.greytHRClone.model;
//
//import lombok.Data;
//
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Entity
//@Table(name="employee_designation")
//public class EmployeeDesignation {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int designationId ;
//
//    @Column(name="designation")
//    private String designation;
//
////    @OneToMany(cascade = CascadeType.ALL)
////    @JoinColumn(name = "employee_des",referencedColumnName = "designationId")
//    @OneToMany(mappedBy = "designation")
//    List<EmployeeData> employeeDataList =new ArrayList<>();
//
//
//}
