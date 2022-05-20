package com.plaxa.pikabu.dto;

import com.plaxa.pikabu.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteCreateRequestDto {

    private VoteType voteType;
    private Integer postId;
}
