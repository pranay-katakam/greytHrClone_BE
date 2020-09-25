package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
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
    private FeedType feedType=FeedType.COMPANY_NEWS;

    @Enumerated
    private EventType eventType= EventType.OTHERS;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @OneToMany( mappedBy="fcId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List< Comment > comments ;

    @OneToMany( mappedBy="flId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Liked> likes;


}
