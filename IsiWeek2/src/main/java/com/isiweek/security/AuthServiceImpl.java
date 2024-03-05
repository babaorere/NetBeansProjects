package com.isiweek.security;

import com.isiweek.auth.LoginData;
import com.isiweek.auth.SignupData;
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

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(
            UserRepository inUserRepository,
            PasswordEncoder inPasswordEncoder,
            AuthenticationManager inAuthenticationManager
    ) {
        this.authenticationManager = inAuthenticationManager;
        this.userRepository = inUserRepository;
        this.passwordEncoder = inPasswordEncoder;
    }

    @Override
    public Optional<User> signup(SignupData inRegisterData) {

        User user = User.builder().username(inRegisterData.getUsername())
                .password(passwordEncoder.encode(inRegisterData.getPassword())).build();

        user = userRepository.save(user);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> login(LoginData inLoginData) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        inLoginData.getUsername(),
                        inLoginData.getPassword()
                )
        );

        User user = userRepository.findByUsernameIgnoreCase(inLoginData.getUsername())
                .orElseThrow();

        return Optional.ofNullable(user);
    }

}
