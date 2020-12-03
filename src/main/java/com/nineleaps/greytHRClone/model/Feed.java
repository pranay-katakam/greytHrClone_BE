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
    private FeedType feedType;

    @Enumerated
    private EventType eventType;

    @Column(name = "name")
    private String name;

    @Column(name="image_suffix")
    private int imageSuffix;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "no_of_years")
    private int noOfYears = 0;

    @OneToMany( mappedBy="feedId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)//here feedId is from comment table
    private List< Comment > comments ;

    @OneToMany( mappedBy="feedId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Liked> likes;


}
