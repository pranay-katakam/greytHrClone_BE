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

////    first in time
//    @Column(name = "first_in")
//    private Date firstIn;
//
////    last out time
//    @Column(name = "last_out")
//    private Date lastOut;

    //date

    @Enumerated
    private AttendanceCategory attendanceCategory;
}
