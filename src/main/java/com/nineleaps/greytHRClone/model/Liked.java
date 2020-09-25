package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "liked")
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    @Column(name="liked_by")
    private String likedBy;

    @Column(name="fl_id")
    private int flId;


}
