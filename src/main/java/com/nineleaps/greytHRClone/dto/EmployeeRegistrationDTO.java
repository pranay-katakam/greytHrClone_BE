package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.CompanyLocation;
import com.nineleaps.greytHRClone.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegistrationDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @NotNull(message = "dob cannot be empty")
    private Timestamp dob;
    @NotNull(message = "location cannot be empty")
    private int locationId;
    @NotNull(message = "contact number cannot be empty")
    private long contactNumber;
    @Enumerated
    private Gender gender;
    private int managerId;
    private List<Integer> departmentId;
    private int designationId;



}
