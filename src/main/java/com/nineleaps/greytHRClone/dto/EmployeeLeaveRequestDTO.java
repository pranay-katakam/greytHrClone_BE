package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.Leavetype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLeaveRequestDTO {
    private int userId;
    private Leavetype leavetype;
    private String reason;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
