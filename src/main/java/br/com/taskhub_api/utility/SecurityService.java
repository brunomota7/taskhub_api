package br.com.taskhub_api.utility;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {

    public UUID getAuthenticatedUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new BadCredentialsException("Usuário não autenticado");
        }

        String userId = jwtAuth.getToken().getSubject();

        if (userId == null) {
            throw new BadCredentialsException("Token não contém o subject");
        }

        try {
            return UUID.fromString(userId);
        } catch (IllegalArgumentException ex) {
            throw new BadCredentialsException("Formato de UUID inválido no token");
        }
    }

}
