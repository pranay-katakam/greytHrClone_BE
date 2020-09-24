package com.nineleaps.greytHRClone.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nineleaps.greytHRClone.dto.EventDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedId;

    @Enumerated
    private FeedType feedType;

    @Enumerated
    private EventType eventType;

    @Column(name = "message")
    private String message;

    @Column(name = "created_date")
    private Date createdDate = new Date();


    @OneToMany(targetEntity=Comment.class, mappedBy="feed",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List< Comment > comments = new ArrayList< >();

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "pl_lid", referencedColumnName = "like_id")
//    private List< Like > likes = new ArrayList< >();




}
