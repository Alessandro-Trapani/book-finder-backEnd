package com.alessandro.book_finder_backend.registration.token;

import com.alessandro.book_finder_backend.appUser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@Entity
public class ConfirmationToken {

    public ConfirmationToken() {
    }

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public AppUser getAppUser() {
        return appUser;
    }

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiredAt;
        this.appUser = appUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }
}
