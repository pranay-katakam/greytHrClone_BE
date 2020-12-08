package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "employee_leave")
public class EmployeeLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

    @Enumerated
    private AttendanceCategory leavetype;

    @NotNull
    @NotEmpty
    @Column(name = "reason")
    private String reason;

    @Column(name="leave_date")
    private LocalDate leaveDate;

    @Column(name="applied_date")
    private LocalDate appliedDate = LocalDate.now();

//    @NotNull
//    @Column(name = "from_date")
//    private LocalDateTime fromDate;
//
//    @NotNull
//    @Column(name = "to_date")
//    private LocalDateTime toDate;

    @Enumerated
    private LeaveStatus leaveStatus;

}
