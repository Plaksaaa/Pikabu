package com.plaxa.pikabu.service;

import com.plaxa.pikabu.dto.AuthenticationResponse;
import com.plaxa.pikabu.dto.LoginRequestDto;
import com.plaxa.pikabu.dto.RefreshTokenRequest;
import com.plaxa.pikabu.dto.RegisterRequestDto;
import com.plaxa.pikabu.entity.Role;
import com.plaxa.pikabu.entity.User;
import com.plaxa.pikabu.entity.VerificationToken;
import com.plaxa.pikabu.repository.UserRepository;
import com.plaxa.pikabu.repository.VerificationTokenRepository;
import com.plaxa.pikabu.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequestDto registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(Instant.now())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        String token = jwtProvider.generateTokenWithUsername(user.getUsername());
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public AuthenticationResponse login(LoginRequestDto loginRequest) {
        String username = loginRequest.getUsername();
        String token = jwtProvider.generateTokenWithUsername(username);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
//                .refreshToken(refreshTokenRequest.getRefreshToken())
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
