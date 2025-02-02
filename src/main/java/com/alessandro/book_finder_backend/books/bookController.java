package com.alessandro.book_finder_backend.books;

import com.alessandro.book_finder_backend.appUser.AppUser;
import com.alessandro.book_finder_backend.login.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/books")
public class bookController {
    @PostMapping("")
    public String createAuthenticationToken() {
       return "hello";
    }
}
