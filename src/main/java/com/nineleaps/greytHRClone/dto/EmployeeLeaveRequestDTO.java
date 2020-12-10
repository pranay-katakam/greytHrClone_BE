package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.AttendanceCategory;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLeaveRequestDTO {
    private int userId;
    private AttendanceCategory leavetype;
    private String reason;
    private LocalDate fromDate;
    private LocalDate toDate;
}
