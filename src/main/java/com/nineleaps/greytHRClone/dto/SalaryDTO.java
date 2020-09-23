package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {

    int basic;
    int hra;
    int specialAllowance;
    int pf;
    int prof_tax;
    int group_medical_deduction;
    int group_policy_accident_deduction;
    int netDeduction;
    int netPay;
    int id;
}
