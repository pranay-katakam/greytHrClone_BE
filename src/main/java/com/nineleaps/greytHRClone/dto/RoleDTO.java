package com.nineleaps.greytHRClone.dto;

import com.nineleaps.greytHRClone.model.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDTO {
    @Enumerated(EnumType.STRING)
    private UserRoles role;
}
