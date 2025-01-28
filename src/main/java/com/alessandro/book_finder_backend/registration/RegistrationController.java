package com.alessandro.book_finder_backend.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
        public String register(@RequestBody RegistrationRequest userRegistrationDto) {
            System.out.println("registered user");
            return registrationService.register(userRegistrationDto);
        }
    }


