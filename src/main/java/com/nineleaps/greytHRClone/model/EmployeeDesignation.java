//package com.nineleaps.greytHRClone.model;
//
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Getter
//@Setter
//@ToString
//
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
//    public int getDesignationId() {
//        return designationId;
//    }
//
//    public void setDesignationId(int designationId) {
//        this.designationId = designationId;
//    }
//
//    public String getDesignation() {
//        return designation;
//    }
//
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }
//
//    @Override
//    public String toString() {
//        return "EmployeeDesignation{" +
//                "designationId=" + designationId +
//                ", designation='" + designation + '\'' +
//                '}';
//    }
//}
