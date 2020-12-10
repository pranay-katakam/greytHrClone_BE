package com.nineleaps.greytHRClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {
    private int userId;
    private int feedId;

    @NotNull(message = "comment cannot be null")
    @NotEmpty(message = "comment cannot be empty")
    private String comment;
}
