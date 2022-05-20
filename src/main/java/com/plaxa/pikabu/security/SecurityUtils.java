package com.plaxa.pikabu.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
