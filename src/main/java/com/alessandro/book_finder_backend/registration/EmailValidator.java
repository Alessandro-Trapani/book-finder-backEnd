package com.alessandro.book_finder_backend.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Configuration
public class EmailValidator implements Predicate<String> {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");



    @Override
    public boolean test(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
