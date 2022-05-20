package com.plaxa.pikabu.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

//    public String generateToken(Authentication authentication) {
//        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
//        return generateTokenWithUsername(principal.getUsername());
//    }

    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setSubject(username)
                .setIssuedAt(
                        Date.from(
                                LocalDateTime.now()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        )
                )
                .setExpiration(
                        Date.from(
                                LocalDateTime.now()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant().plusMillis(jwtExpirationInMillis)
                        ))
                .compact();

//        return jwtProperties.getTokenPrefix() + token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.info("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.info("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.info("Malformed jwt");
        } catch (SignatureException sEx) {
            log.info("Invalid signature");
        } catch (Exception e) {
            log.info("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
