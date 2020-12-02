package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.DoorAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwipesDTO {
    private int swipeId;
    private int employeeId;
    private String employeeName;
    private LocalDateTime createdDate;
    private String doorAddress;
}
