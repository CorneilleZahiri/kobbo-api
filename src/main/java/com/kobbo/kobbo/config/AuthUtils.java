package com.kobbo.kobbo.config;

import com.kobbo.kobbo.exception.InvalideArgumentException;
import com.kobbo.kobbo.service.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    public Jwt getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            return (Jwt) authentication.getPrincipal();
        }
        throw new InvalideArgumentException("Jwt inexistant");
    }
}
