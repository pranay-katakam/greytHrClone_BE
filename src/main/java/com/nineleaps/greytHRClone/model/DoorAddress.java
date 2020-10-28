package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "door_address")
public class DoorAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @Column(name="door_name")
    private String doorName;
}
