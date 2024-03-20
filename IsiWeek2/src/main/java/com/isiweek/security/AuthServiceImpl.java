package com.isiweek.security;

import com.isiweek.auth.LoginData;
import com.isiweek.auth.SignupData;
import com.isiweek.role.Role;
import com.isiweek.role.RoleRepository;
import com.isiweek.status.Status;
import com.isiweek.status.StatusEnum;
import com.isiweek.status.StatusRepository;
import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;

    private final StatusRepository statusRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(
            RoleRepository inRoleRepository,
            StatusRepository inStatusRepository,
            UserRepository inUserRepository,
            PasswordEncoder inPasswordEncoder,
            AuthenticationManager inAuthenticationManager
    ) {
        this.roleRepository = inRoleRepository;
        this.statusRepository = inStatusRepository;
        this.authenticationManager = inAuthenticationManager;
        this.userRepository = inUserRepository;
        this.passwordEncoder = inPasswordEncoder;
    }

    @Override
    public Optional<User> signup(SignupData inRegisterData) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByEmailIgnoreCase(inRegisterData.getEmail())) {
            throw new UserAlreadyExistsException("User with email: " + inRegisterData.getEmail() + " already exists.");
        }

        Role userRole = roleRepository.findById(inRegisterData.getRole())
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + inRegisterData.getRole()));

        Status status = statusRepository.findByName(StatusEnum.PENDING.name())
                .orElseThrow(() -> new StatusNotFoundException("Status not found: " + StatusEnum.PENDING.name()));

        User user = User.builder().email(inRegisterData.getEmail().toLowerCase())
                .password(passwordEncoder.encode(inRegisterData.getPassword()))
                .role(userRole)
                .status(status)
                .build();

        user = userRepository.save(user);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> login(LoginData inLoginData) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        inLoginData.getEmail(),
                        inLoginData.getPassword()
                )
        );

        User user = userRepository.findByEmailIgnoreCase(inLoginData.getEmail())
                .orElseThrow();

        return Optional.ofNullable(user);
    }

}
