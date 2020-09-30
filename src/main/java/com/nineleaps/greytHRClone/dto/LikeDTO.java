package com.nineleaps.greytHRClone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {

    private int eid;
    private String likedBy;
    private Date likedOn ;

}

