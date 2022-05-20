package com.plaxa.pikabu.controller;

import com.plaxa.pikabu.dto.AuthenticationResponse;
import com.plaxa.pikabu.dto.LoginRequestDto;
import com.plaxa.pikabu.dto.RefreshTokenRequest;
import com.plaxa.pikabu.dto.RegisterRequestDto;
import com.plaxa.pikabu.security.JwtProvider;
import com.plaxa.pikabu.service.AuthService;
import com.plaxa.pikabu.service.RefreshTokenService;
import com.plaxa.pikabu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
//        String token = userService.findByPasswordAndUsername(registerRequest.getPassword(), registerRequest.getUsername())
//                .map(UserReadDto::getUsername)
//                .map(jwtProvider::generateTokenWithUsername)
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
//        return new AuthenticationResponse(token);
    }


    //    @GetMapping("accountVerification/{token}")
//    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
//        authService.verifyAccount(token);
//        return new ResponseEntity<>("Account Activated Successfully", OK);
//    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Validated @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Validated @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
