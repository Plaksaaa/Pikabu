package com.plaxa.pikabu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReadDto {

    private Integer id;
    private String title;
    private String description;
    private String username;
    private Integer voteCount;
    private Integer commentCount;
    private boolean upVote;
    private boolean downVote;
}
