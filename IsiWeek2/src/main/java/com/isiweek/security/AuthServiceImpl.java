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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
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

        log.info("signup Inmediatamente al entrar");

        // Verificar si el usuario ya existe
        if (userRepository.existsByEmailIgnoreCase(inRegisterData.getEmail())) {
            throw new UserAlreadyExistsException("User with email: " + inRegisterData.getEmail() + " already exists.");
        }

        log.info("Antes de recuperar el Rol");
        Role userRole = roleRepository.findById(inRegisterData.getIdRole())
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + inRegisterData.getIdRole()));

        log.info("Antes de recuperar el status");
        Status status = statusRepository.findByName(StatusEnum.PENDING.name())
                .orElseThrow(() -> new StatusNotFoundException("Status not found: " + StatusEnum.PENDING.name()));

        log.info("Antes de crear el User");
        User user = User.builder().email(inRegisterData.getEmail().toLowerCase())
                .password(passwordEncoder.encode(inRegisterData.getPassword()))
                .role(userRole)
                .status(status)
                .build();

        log.info("Antes de guardar el User");
        user = userRepository.save(user);

        log.info("Antes de retornar");
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
