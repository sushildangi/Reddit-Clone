package com.luv2tech.security;

import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String generateToken(Authentication authentication);
    boolean validateToken(String jwt);
    String getUsernameFromJWT(String token);
}
