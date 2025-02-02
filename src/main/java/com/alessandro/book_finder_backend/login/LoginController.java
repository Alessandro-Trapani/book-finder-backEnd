package com.alessandro.book_finder_backend.login;

import com.alessandro.book_finder_backend.appUser.AppUser;
import com.alessandro.book_finder_backend.appUser.AppUserService;
import com.alessandro.book_finder_backend.login.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> getJWTToken(@RequestBody LoginDto loginCredentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error","Bad credentials." ));
        }

        AppUser user = appUserService.findByEmail(loginCredentials.getEmail()).orElseThrow();

        if(  !user.isAccountNonLocked()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error","Account is locked, check email to unlock it" ));
        }
        final UserDetails userDetails = appUserService.loadUserByUsername(loginCredentials.getEmail());
        final String jwt = JwtUtil.generateToken(user);



        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("jwt",jwt ));
    }
}
