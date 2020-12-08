package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "liked")
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    @Column(name="feed_id")
    private int feedId;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

}
