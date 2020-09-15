package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private String name;
    private String designation;
    private String department;
    private int managerId;
    private String managerName;
    private String location;

}
