package com.plaxa.pikabu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequestDto {

    private Integer id;
    private String message;
    private Integer postId;
    private Instant createdDate;
    private String username;
}
