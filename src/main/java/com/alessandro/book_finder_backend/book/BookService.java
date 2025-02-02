package com.alessandro.book_finder_backend.book;

import com.alessandro.book_finder_backend.appUser.AppUser;
import com.alessandro.book_finder_backend.appUser.AppUserRepository;
import com.alessandro.book_finder_backend.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {

    private final AppUserRepository appUserRepository;

    public BookService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public String addBookToUser(String email, String googleBookId) {
        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.addReadBook(googleBookId);
        appUserRepository.save(user);

        return "Book added successfully";
    }

    @Transactional
    public String removeBookFromUser(String email, String googleBookId) {
        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.removeReadBook(googleBookId);
        appUserRepository.save(user);

        return "Book removed successfully";
    }

    @Transactional(readOnly = true)
    public Set<String> getBooks(String email) {
        String formattedEmail = email.trim().toLowerCase();
        AppUser user = appUserRepository.findByEmail(formattedEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getReadBookIds();
    }

}
