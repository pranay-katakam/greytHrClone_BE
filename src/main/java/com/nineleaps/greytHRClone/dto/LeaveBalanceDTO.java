package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveBalanceDTO {
    private int earnedLeave;
    private List<LeaveDetailsDTO> earnedLeaveDetails;
    private int paternityLeave;
    private List<LeaveDetailsDTO> paternityLeaveDetails;
    private int sickLeave;
    private List<LeaveDetailsDTO> sickLeaveDetails;
    private int LossOfPay;
    private List<LeaveDetailsDTO> LossOfPayDetails;
    private int MedicalWorkFromHome;
    private List<LeaveDetailsDTO> MedicalWorkFromHomeDetails;
}
