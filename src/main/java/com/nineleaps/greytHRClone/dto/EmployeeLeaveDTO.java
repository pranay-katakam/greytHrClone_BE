package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.LeaveStatus;
import com.nineleaps.greytHRClone.model.Leavetype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLeaveDTO {
    private int leaveId;
    private int empId;
    private String name;
    private int managerId;
    private String managerName;
    private Leavetype leavetype;
    private String reason;
    private Date fromDate;
    private Date toDate;
    private LeaveStatus leaveStatus;


}
