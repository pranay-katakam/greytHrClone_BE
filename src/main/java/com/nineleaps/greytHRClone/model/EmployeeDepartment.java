package com.nineleaps.greytHRClone.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

public class EmployeeDepartment {
    @Data
    @Entity
    @Getter
    @Setter
    @ToString
    @Table(name="employee_data")
    public class EmployeeDesignation {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int departmentId ;

        @Column(name="department")
        private String department;
    }
}
