package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.DoorAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwipesDTO {
    private int swipeId;
    private int employeeId;
    private String employeeName;
    private Date createdDate;
    private String doorAddress;
}
