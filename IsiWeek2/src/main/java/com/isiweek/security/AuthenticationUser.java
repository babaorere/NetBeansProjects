package com.isiweek.security;

import com.isiweek.role.Role;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationUser implements Authentication {

    private String username;
    private String password;
    private Boolean isAuthenticated;
    private List<Role> listRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return listRole.stream().map(roleAux -> new SimpleGrantedAuthority(roleAux.getName())).collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return username;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean inIsAuthenticated) {
        this.isAuthenticated = inIsAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }

}
