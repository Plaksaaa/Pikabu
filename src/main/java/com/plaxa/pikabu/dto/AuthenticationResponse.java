package com.plaxa.pikabu.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
}
