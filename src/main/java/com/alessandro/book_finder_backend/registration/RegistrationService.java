package com.alessandro.book_finder_backend.registration;

import com.alessandro.book_finder_backend.appUser.AppUser;
import com.alessandro.book_finder_backend.appUser.AppUserRepository;
import com.alessandro.book_finder_backend.appUser.AppUserRole;
import com.alessandro.book_finder_backend.appUser.AppUserService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {


    public RegistrationService(AppUserService appUserService, EmailValidator emailValidator) {
        this.appUserService = appUserService;
        this.emailValidator = emailValidator;
    }

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
       if(!isValidEmail){
           throw new IllegalStateException("email not valid");
       }
        return appUserService.signUpUser(new AppUser(request.getFirstName(),request.getLastName(),request.getEmail(),request.getPassword(), AppUserRole.USER, true));
    }
}
