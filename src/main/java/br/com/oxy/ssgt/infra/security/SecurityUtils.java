package br.com.oxy.ssgt.infra.security;

import br.com.oxy.ssgt.infra.execption.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public Long getCurrentUserId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated.");
        }

        if(!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new UnauthorizedException("Invalid authentication Token.");
        }

        Object claim = jwt.getClaim("userId");

        if (claim == null) {
            throw new UnauthorizedException("User ID claim is missing in the token.");
        }

        return Long.valueOf(claim.toString());
    }
}
