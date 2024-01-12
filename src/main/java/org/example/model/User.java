package org.example.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class User {
    private final int id;
    private final String username;
    private final String password;
    private final boolean isAdmin;

    public User() {
        this.id = 0;
        this.username = "";
        this.password = "";
        this.isAdmin = false;
    }

    public User( int id, String username, String password, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getId() {
        return this.id;
    }

    public String getRole() {
        return this.isAdmin ? "ROLE_ADMIN" : "ROLE_USER";
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return isAdmin ?
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")) :
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
