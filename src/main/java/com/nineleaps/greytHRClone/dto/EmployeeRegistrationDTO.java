package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.CompanyLocation;
import com.nineleaps.greytHRClone.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private Date dob;
    private int locationId;
    private long contactNumber;
    @Enumerated
    private Gender gender;
    private int managerId;
    private List<Integer> departmentId;
    private int designationId;



}
