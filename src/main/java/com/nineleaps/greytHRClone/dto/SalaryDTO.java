package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {
private String payDate;
    private int eid;
    private int basic;
    private int hra;
    private int specialAllowance;
    private int pf;
    private int profTax;
    private int GroupMedicalDeduction;
    private int GroupPolicyAccidentDeduction;
    private int totalEarning;
    private int totalDeduction;

    private int netPay;

}
