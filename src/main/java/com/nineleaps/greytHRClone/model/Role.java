package com.nineleaps.greytHRClone.model;

import lombok.Data;
import javax.persistence.*;


@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "role")
    private String role;

}
