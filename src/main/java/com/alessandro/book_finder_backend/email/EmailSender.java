package com.alessandro.book_finder_backend.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public interface EmailSender {
    void send(String to, String email);
}
