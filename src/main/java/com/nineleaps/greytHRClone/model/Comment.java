//package com.nineleaps.greytHRClone.model;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Table(name = "comment")
//public class Comment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long commentId;
//
//    @Column(name="text")
//    private String text;
//
//    @Column(name="commented_by")
//    private String commentedBy;
//
//    @ManyToOne()
//    @JoinColumn(name="fc_id", referencedColumnName = "fc_id", insertable = false, updatable = false)
//    private Feed feed;
//}
