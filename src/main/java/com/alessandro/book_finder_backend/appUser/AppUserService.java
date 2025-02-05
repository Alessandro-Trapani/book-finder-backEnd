package com.alessandro.book_finder_backend.appUser;

import com.alessandro.book_finder_backend.registration.token.ConfirmationToken;
import com.alessandro.book_finder_backend.registration.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {
    public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        appUserRepository.save(new AppUser("ale","alessandro.trapani03@gmail.com","trapani",bCryptPasswordEncoder.encode("asdf"),AppUserRole.USER,false));
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find the user from the repository by email
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        // Return a UserDetails object, which Spring Security expects
        return new org.springframework.security.core.userdetails.User(
                appUser.getEmail(), // Use email as username
                appUser.getPassword(), // Use password from AppUser
                new ArrayList<>() // Authorities (can be added later, for now empty)
        );
    }



    public String signUpUser(AppUser appUser){
        boolean userExists = appUserRepository.findByEmail(appUser.getUsername()).isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");

        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPssword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);


        return token;
    }


    public void enableAppUser(String email) {
        if(appUserRepository.findByEmail(email).isPresent()){
            AppUser appUser = appUserRepository.findByEmail(email).get();
            appUser.setLocked(false);
            appUserRepository.save(appUser);
    }
}

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public void addBookToReadList(String email, String googleBookId) {
        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.addFavBooks(googleBookId);
        appUserRepository.save(user);
    }

    public void removeBookFromReadList(String email, String googleBookId) {
        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.removeFavBook(googleBookId);
        appUserRepository.save(user);
    }

    public Set<String> getReadList(String email) {
        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavBooks();
    }
}
