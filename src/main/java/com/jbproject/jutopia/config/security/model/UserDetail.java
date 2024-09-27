package com.jbproject.jutopia.config.security.model;

import com.jbproject.jutopia.rest.entity.UserEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class UserDetail implements UserDetails {

    private Long id;
    private String userId;
    private String email;
    private String name;
    private String password;
    private int age;
    private String role;

    public UserDetail(UserEntity user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.age = user.getAge();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
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
