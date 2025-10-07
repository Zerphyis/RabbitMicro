package dev.Zerphyis.microRabbitMq.Infra.security;

import dev.Zerphyis.microRabbitMq.Application.services.user.UserService;
import dev.Zerphyis.microRabbitMq.Application.useCases.users.LogoutUserUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FilterSecurity extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final LogoutUserUseCase logoutUserUseCase;
    private final UserService userService;

    public FilterSecurity(JwtTokenProvider jwtTokenProvider, LogoutUserUseCase logoutUserUseCase, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.logoutUserUseCase = logoutUserUseCase;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (!logoutUserUseCase.isTokenBlacklisted(token)
                    && jwtTokenProvider.validateToken(token)) {

                String email = jwtTokenProvider.getUsernameFromToken(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(email);

                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
