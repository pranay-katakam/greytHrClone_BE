package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCommentDTO {
    private String reply;
    private String repliedBy;
    private Date repliedOn;
}
