package com.nineleaps.greytHRClone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String comment;
    private String commentedBy;
    private Date commentedOn;

}
