package com.plaxa.pikabu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateRequestDto {

    private Integer id;
    private String title;
    private Integer postId;
}
