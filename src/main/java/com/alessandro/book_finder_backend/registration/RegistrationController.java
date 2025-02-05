package com.alessandro.book_finder_backend.registration;

import com.alessandro.book_finder_backend.exception.EmailAlreadyConfirmedException;
import com.alessandro.book_finder_backend.exception.EmailAlreadyExistsException;
import com.alessandro.book_finder_backend.exception.TokenExpiredException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(path = "register")
    public ResponseEntity<?> register( @RequestBody RegistrationRequest userRegistrationDto) {
        try{
           String token =  registrationService.register(userRegistrationDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("token", token));
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid email address. Please provide a valid email."));

        }
        catch (EmailAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email already exist."));

        }

    }

    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        try {

            String confirmation = registrationService.confirmToken(token);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("token", token));
        } catch (EmailAlreadyConfirmedException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email already confirmed."));
        } catch (TokenExpiredException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Token expired. Please request a new confirmation email."));
        }
    }

}


