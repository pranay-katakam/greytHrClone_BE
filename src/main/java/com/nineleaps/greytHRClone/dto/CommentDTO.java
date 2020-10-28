package com.nineleaps.greytHRClone.dto;


import com.nineleaps.greytHRClone.model.ReplyComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private int commentId;
    private String comment;
    private String commentedBy;
    private String commentedByImage;
    private Date commentedOn;
    private List<ReplyCommentDTO> replies;

}
