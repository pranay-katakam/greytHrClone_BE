package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.CompanyLocation;
import com.nineleaps.greytHRClone.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.*;
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

//    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message="password rule violated,must be a combination of upper case,lower case,digit and a special character")
    @NotEmpty(message = "password cannot be empty")
    private String password;

    @NotNull(message = "dob cannot be empty")
    private Timestamp dob;

    @NotNull(message = "locationId cannot be empty")
    private Integer locationId;

    @Pattern(regexp="(^$|[0-9]{10})",message = "contactNumber should contain 10 digits")
    private String contactNumber;

    @Enumerated
    private Gender gender;

    private int managerId;

    @NotNull(message = "departmentId cannot be empty")
    private List<Integer> departmentId;

    @NotNull(message = "designationId cannot be empty")
    private Integer designationId;



}
