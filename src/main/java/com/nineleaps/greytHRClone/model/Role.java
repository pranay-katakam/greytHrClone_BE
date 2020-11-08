package com.nineleaps.greytHRClone.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Collection;



@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;

    @ManyToMany(mappedBy = "roles")
    private Collection<EmployeeData> users;

    @Enumerated(EnumType.STRING)
    private UserRoles role;
}
