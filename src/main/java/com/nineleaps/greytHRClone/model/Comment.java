package com.nineleaps.greytHRClone.model;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private EmployeeData user;

    @Column(name = "fc_id")
    private int fcId;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @OneToMany( mappedBy="cid",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReplyComment> replies ;





}
