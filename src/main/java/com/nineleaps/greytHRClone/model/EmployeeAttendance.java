package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "employee_attendance")
public class EmployeeAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Enumerated
    private AttendanceCategory attendanceCategory;
}
