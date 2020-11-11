package com.nineleaps.greytHRClone.model;

import lombok.Data;
import javax.persistence.*;


@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private UserRoles role;
}
