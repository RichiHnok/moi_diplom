package com.richi.web_part.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.richi.common.entity.User;

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
        var authoritiesList = new ArrayList<SimpleGrantedAuthority>();
        authoritiesList.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return authoritiesList;
        // return user.getUserRole().stream()
        //     .map(role -> role.getRoleName())
        //     .map(SimpleGrantedAuthority::new)
        //     .collect(Collectors.toList());
        
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

    public User getUser() {
        return user;
    }
}
