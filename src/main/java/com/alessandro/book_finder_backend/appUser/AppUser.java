package com.alessandro.book_finder_backend.appUser;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode
@Entity
public class AppUser implements UserDetails {


    @Id
    @SequenceGenerator(
            name = "appUser_sequence",
            sequenceName = "appUser_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appUser_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "favourite_books",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "google_book_id")
    private Set<String> favBooks = new HashSet<>();

    public String getEmail() {
        return email;
    }

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked = false;

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public AppUser(String firstName, String email, String lastName, String password, AppUserRole appUserRole, Boolean locked) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.password = password;
        this.appUserRole = appUserRole;
        this.locked = locked;
    }

    public AppUserRole getAppUserRole() {
        return appUserRole == null ?  AppUserRole.USER : appUserRole;
    }

    public AppUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }


    public void setPssword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<String> getFavBooks() {
        return favBooks;
    }

    public void setFavBooks(Set<String> readBookIds) {
        this.favBooks = readBookIds;
    }


    public void addFavBooks(String bookId) {
        this.favBooks.add(bookId);
    }

    public void removeFavBook(String bookId) {
        this.favBooks.remove(bookId);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", readBookIds=" + favBooks +
                ", appUserRole=" + appUserRole +
                ", locked=" + locked +
                '}';
    }
}
