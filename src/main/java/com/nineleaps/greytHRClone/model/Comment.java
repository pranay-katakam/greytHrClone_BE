package com.nineleaps.greytHRClone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(name="text")
    private String text;

    @Column(name="commented_by")
    private String commentedBy;

    @Column(name="fc_id")
    private int fcId;


}
