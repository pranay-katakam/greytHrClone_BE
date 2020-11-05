package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.DoorAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwipeDTO {
    private int userId;
    private int doorAddressId;
}
