package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private int eid;
    private String name;
    private String designation;
    private List<String> department;
    private int managerId;
    private String managerName;
    private String location;
    private String imageName;


}
