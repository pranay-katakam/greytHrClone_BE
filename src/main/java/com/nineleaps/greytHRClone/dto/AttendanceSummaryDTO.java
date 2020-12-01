package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceSummaryDTO {
    private int presentDays;
    private int absent;
    private int onLeave;
    private int holidays;
    private int lateInDays;
    private int earlyOutDays;
    private List<AttendanceDetailsDTO> attendanceDetails;

}
