package com.isiweek.security;

import com.isiweek.exceptions.UserNotFoundException;
import com.isiweek.role.Role;
import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUsersDetailsService(UserRepository inUserRepository) {
        this.userRepository = inUserRepository;
    }

    //Método para traernos una lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList(); // Devuelve una lista vacía si la lista de roles es nula o vacía
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    //Método para traernos un usuario con todos sus datos por medio de sus email
    @Override
    public UserDetails loadUserByUsername(String inEmail) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmailIgnoreCase(inEmail);

        if (user == null || user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                mapToAuthorities((List<Role>) user.get().getRoles())
        );
    }
}
