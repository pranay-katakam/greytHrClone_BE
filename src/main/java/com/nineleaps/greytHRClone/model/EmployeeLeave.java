package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    private Leavetype leavetype;

    @NotNull
    @NotEmpty
    @Column(name = "reason")
    private String reason;

    @NotNull
    @Column(name = "from_date")
    private Date fromDate;

    @NotNull
    @Column(name = "to_date")
    private Date toDate;

    @Enumerated
    private LeaveStatus leaveStatus;

}
