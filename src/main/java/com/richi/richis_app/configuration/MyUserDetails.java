package com.richi.richis_app.configuration;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.richi.richis_app.entity.User;

public class MyUserDetails implements UserDetails{
    
    private User user;

    public MyUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // System.out.println("\n" + user.getUsersRoles().stream()
        // .map(role -> role.getRoleName())
        // .map(SimpleGrantedAuthority::new)
        // .collect(Collectors.toList()) + "\n");
        return user.getUsersRoles().stream()
            .map(role -> role.getRoleName())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        // return Arrays.stream(user.getRole().split(", "))
        //     .map(SimpleGrantedAuthority::new)
        //     .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
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
