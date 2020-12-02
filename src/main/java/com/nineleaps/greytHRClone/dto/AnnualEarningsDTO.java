package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualEarningsDTO {

    private int annualGrossPay;
    private int annualNetDeduction;
    private int annualNetPay;
    private int annualPf;
    private List<SalaryDTO> yearlySalary;

}
