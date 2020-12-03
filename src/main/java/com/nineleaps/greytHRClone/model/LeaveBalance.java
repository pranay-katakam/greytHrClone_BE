package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "leave_balance")
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveBalanceId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

    @Column(name = "date")
    private LocalDate date=LocalDate.now();

    @Column(name = "earned_leave")
    private int EarnedLeave;

    @Column(name = "paternity_leave")
    private int PaternityLeave;

    @Column(name = "sick_leave")
    private int SickLeave;

    @Column(name = "medical_work_from_home")
    private int MedicalWorkFromHome;

    @Column(name = "loss_of_pay")
    private int LossOfPay;


}
