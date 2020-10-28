package com.nineleaps.greytHRClone.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "reply_comment")
public class ReplyComment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int replyCId;

        @Column(name = "reply")
        private String reply;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "empId", referencedColumnName = "empId")
        private EmployeeData user;

        @Column(name = "cId")
        private int cId;

        @Column(name = "created_date")
        private Date createdDate = new Date();
}