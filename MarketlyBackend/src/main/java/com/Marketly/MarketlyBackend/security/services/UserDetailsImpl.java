package com.Marketly.MarketlyBackend.security.services;

import com.Marketly.MarketlyBackend.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@Component
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID=1L;
    private long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<?extends GrantedAuthority>authorities;

    public UserDetailsImpl(long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    public UserDetailsImpl build(User user){
        List<GrantedAuthority>grantedAuthorities=user.getRole().stream().map(role->new SimpleGrantedAuthority(role.getRoleName().toString())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getUserId(),user.getUserName(),user.getEmail(),user.getPassword(), grantedAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
    @Override
    public boolean equals(Object O){
          if(this==O) return true;
          if(O==null || getClass()!=O.getClass()) return false;
         UserDetailsImpl userDetails=(UserDetailsImpl) O;
           return Objects.equals(id,userDetails.id);
    }
}
