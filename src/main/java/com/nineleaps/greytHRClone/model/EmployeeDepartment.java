package com.nineleaps.greytHRClone.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class EmployeeDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int departmentId ;

    @Column(name="department")
    private String department;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "EmployeeDepartment{" +
                "departmentId=" + departmentId +
                ", department='" + department + '\'' +
                '}';
    }
}
