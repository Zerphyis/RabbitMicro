package dev.Zerphyis.microRabbitMq.Infra.security;

import dev.Zerphyis.microRabbitMq.Application.useCases.users.LogoutUserUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FilterSecurity extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final LogoutUserUseCase logoutUserUseCase;
    private final UserDetailsService userDetailsService;

    public FilterSecurity(JwtTokenProvider jwtTokenProvider,
                          LogoutUserUseCase logoutUserUseCase,
                          UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.logoutUserUseCase = logoutUserUseCase;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (!logoutUserUseCase.isTokenBlacklisted(token) && jwtTokenProvider.validateToken(token)) {

                    String email = jwtTokenProvider.getUsernameFromToken(token);

                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                        var authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido ou expirado");
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Erro ao validar token: " + e.getMessage());
        }
    }
}
