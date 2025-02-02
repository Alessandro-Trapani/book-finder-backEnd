package com.alessandro.book_finder_backend.login.jwt;
import com.alessandro.book_finder_backend.appUser.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    // Secret key for signing (should be stored securely)
    static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token expiration time (e.g., 1 hour)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // Generate a JWT token for an AppUser
    public static String generateToken(AppUser user) {
        // Create a map to store non-sensitive user information
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("email", user.getEmail());  // Add email
        userClaims.put("firstName", user.getFirstName());  // Add first name
        userClaims.put("lastName", user.getLastName());  // Add last name
        userClaims.put("role", user.getAppUserRole().toString());  // Add role as a string (could be changed to a different format)

        // Build the JWT with the user details
        return Jwts.builder()
                .setSubject(user.getUsername())  // Set username as subject
                .addClaims(userClaims)  // Add custom user claims
                .setIssuedAt(new Date())  // Issued time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // Sign with secret key
                .compact();
    }
}
