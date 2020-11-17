package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.AttendanceCategory;
import com.nineleaps.greytHRClone.model.LeaveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private AttendanceCategory leavetype;
    private String reason;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private LeaveStatus leaveStatus;
}
