package com.alessandro.book_finder_backend.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.alessandro.book_finder_backend.login.jwt.JwtUtil.SECRET_KEY;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {


        // Step 1: Extract the JWT token from the Authorization header
        String token = extractTokenFromHeader(request);

        // Step 2: If the token is valid, process it
        if (token != null && isValidToken(token)) {
            String username = getUsernameFromToken(token);

            // Step 3: Create the authentication token and set it in the security context
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, null);  // No credentials and authorities here

            // Step 4: Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Step 5: Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }

    // Helper method to extract JWT token from the Authorization header
    private String extractTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the header exists and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());  // Extract token
        }

        return null;
    }

    // Helper method to validate the JWT token
    private boolean isValidToken(String token) {
        try {
            // Parse the JWT to ensure it's valid and not expired
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)  // Use the same secret key as when generating the token
                    .parseClaimsJws(token);  // If the token is invalid or expired, this will throw an exception

            return true;
        } catch (Exception e) {
            // If any exception occurs (e.g., token expired or invalid), return false
            return false;
        }
    }

    // Helper method to extract the username (subject) from the JWT
    private String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Use the same secret key as when generating the token
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();  // Extract the subject (username)
    }


}
