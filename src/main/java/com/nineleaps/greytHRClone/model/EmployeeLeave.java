package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "employee_leave")
public class EmployeeLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

    @Enumerated
    private Leavetype leavetype;

    @Column(name = "reason")
    private String reason;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Enumerated
    private LeaveStatus leaveStatus;

}
