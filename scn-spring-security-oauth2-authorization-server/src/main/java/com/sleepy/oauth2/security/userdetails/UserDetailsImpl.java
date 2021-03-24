package com.sleepy.oauth2.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;

    private boolean accountNonLocked;
    private boolean accountEnabled;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public UserDetailsImpl() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public boolean isEnabled() {
        return accountEnabled;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }
}
