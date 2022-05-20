package com.plaxa.pikabu.dto;

import com.plaxa.pikabu.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDto {

    private Long id;
    private String username;
    private String password;
    private Instant createdAt;
    private Role role;
}
