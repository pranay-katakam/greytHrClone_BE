package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "company_location")
public class CompanyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    @Column(name="location_name")
    private String locationName;
}
