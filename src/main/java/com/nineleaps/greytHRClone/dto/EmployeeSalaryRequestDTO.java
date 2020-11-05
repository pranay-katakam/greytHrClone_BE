package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.Leavetype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryRequestDTO {
    private int eid;
    private int totalSalary;
}
