package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalTimeDTO {
    private int employeeId;
    private String name;
    private LocalDateTime firstIn;
    private LocalDateTime lastOut;
    private long totalTime;


}
