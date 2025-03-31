package org.example.bxbatuz.Security;

import org.example.bxbatuz.Entity.Auth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UsersDetails implements UserDetails {

    private final Auth auth;

    public UsersDetails(Auth auth) {
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority(auth.getRole().toString());
        return Collections.singleton(simpleGrantedAuthority);    }

    @Override
    public String getPassword() {
        return auth.getPassword();
    }

    @Override
    public String getUsername() {
        return auth.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
