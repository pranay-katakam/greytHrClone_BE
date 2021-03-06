package com.nineleaps.greytHRClone.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.nineleaps.greytHRClone.model.EventType;
import com.nineleaps.greytHRClone.model.FeedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedDTO {
    private int feedId;
    private String name;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private FeedType feedType;

    private Date createdDate;
    private List<CommentDTO> comments;
    private List<LikeDTO> likes;
    private int numberOfYears=0;

//
//    public enum EventType {
//        Birthday, Anniversary
//    }

}
