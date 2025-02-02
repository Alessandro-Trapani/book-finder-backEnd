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
    private String email;
    private String password;

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
}
