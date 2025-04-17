package fr.eni.cave.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserDetailsService userDetailsService;

    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Début du filtre d'authentification JWT");
        // vérifier le jeton JWT – il est passé dans l’en-tête
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        // vérifier qu’il y a une donnée dans l’entête qui correspond à Authorization
        // l’entête contient Bearer <jeton> SINON erreur
        // Sinon laissé le comportement suivre son cours
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Il y a un JWT – il faut l’extraire
        jwt = authHeader.substring(7);// 7 correspond à Bearer

        // Vérification de l'utilisateur
        final String userName = jwtService.extractUserName(jwt);

        // Validation des données par rapport à la DB
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Check in DB
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Si valide - servlet ok
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
