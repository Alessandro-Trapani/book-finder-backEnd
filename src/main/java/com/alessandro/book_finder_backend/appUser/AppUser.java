package com.alessandro.book_finder_backend.appUser;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
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
    private final String firstName;
    private final String lastName;
    private final String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private final AppUserRole appUserRole;
    private Boolean locked = false;

    public AppUser(String firstName, String email, String lastName, String password, AppUserRole appUserRole, Boolean locked) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.password = password;
        this.appUserRole = appUserRole;
        this.locked = locked;
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
}
