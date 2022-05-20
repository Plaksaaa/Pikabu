package com.plaxa.pikabu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTagsDto {

    private Integer postId;
    private Integer tagId;
}
