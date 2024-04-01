package com.isiweek.security;

import com.isiweek.role.Role;
import java.util.Collection;
import java.util.Collections;
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

    private String email;
    private String password;
    private Boolean isAuthenticated;
    private List<Role> listRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (listRole == null) {
            return Collections.emptyList();
        }

        /*
            Code snippet utilizes Java Stream API to transform a list of Role objects (listRole) into a list of 
            SimpleGrantedAuthority objects. Each Role object is mapped to a SimpleGrantedAuthority object representing its name.
         */
        List<SimpleGrantedAuthority> authorities = listRole != null
                ? listRole.stream().map(roleAux -> new SimpleGrantedAuthority(roleAux.getName())).collect(Collectors.toList())
                : Collections.emptyList();

        return authorities;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return email;
    }

    @Override
    public Object getPrincipal() {
        return email;
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
        return email;
    }

}
