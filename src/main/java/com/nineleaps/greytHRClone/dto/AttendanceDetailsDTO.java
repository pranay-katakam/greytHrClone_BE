package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.AttendanceCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDetailsDTO {
    private String date;
    private List<LocalDateTime> swipes;
    private String firstIn;
    private String lastOut;
    private AttendanceCategory status;
    private String totalWorkHours;
    private String excessHours;
    private String lateInHours;
    private String earlyOutHours;
    private LocalDateTime regularizedOn;//------
    private String regularizedBy;//for regularization
    private String remark;//-------------
}
