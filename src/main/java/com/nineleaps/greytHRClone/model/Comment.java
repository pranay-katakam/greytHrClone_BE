package com.nineleaps.greytHRClone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "commented_by")
    private String commentedBy;


    @Column(name = "fc_id")
    private int fcId;

    @Column(name = "created_date")
    private Date createdDate = new Date();


}
