package com.alessandro.book_finder_backend.registration;
import com.alessandro.book_finder_backend.appUser.AppUser;
import com.alessandro.book_finder_backend.appUser.AppUserRole;
import com.alessandro.book_finder_backend.appUser.AppUserService;
import com.alessandro.book_finder_backend.registration.token.ConfirmationToken;
import com.alessandro.book_finder_backend.registration.token.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {


    public RegistrationService(ConfirmationTokenService confirmationTokenService, AppUserService appUserService, EmailValidator emailValidator) {
        this.confirmationTokenService = confirmationTokenService;
        this.appUserService = appUserService;
        this.emailValidator = emailValidator;
    }

    private final ConfirmationTokenService confirmationTokenService;
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    @Transactional
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
       if(!isValidEmail){
           throw new IllegalStateException("email not valid");
       }
        return appUserService.signUpUser(new AppUser(request.getFirstName(),request.getLastName(),request.getEmail(),request.getPassword(), AppUserRole.USER, true));
    }

    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");

           }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");

        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
