package com.alessandro.book_finder_backend.login;

import com.alessandro.book_finder_backend.appUser.AppUser;
import com.alessandro.book_finder_backend.appUser.AppUserService;
import com.alessandro.book_finder_backend.login.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/login")
public class LoginController {


    @Autowired
    private final AuthenticationManager authenticationManager;

    private final AppUserService appUserService;


    public LoginController(AuthenticationManager authenticationManager, AppUserService appUserService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.appUserService = appUserService;

    }


    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AppUser appUser) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = appUserService.loadUserByUsername(appUser.getUsername());
        final String jwt = JwtUtil.generateToken(appUser);

        return ResponseEntity.ok(jwt);
    }
}
