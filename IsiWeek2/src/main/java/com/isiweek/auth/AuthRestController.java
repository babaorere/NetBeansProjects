package com.isiweek.auth;

import com.isiweek.security.AuthServiceImpl;
import com.isiweek.security.AuthenticationUser;
import com.isiweek.security.JwtAuthResponse;
import com.isiweek.security.JwtTokenProvider;
import com.isiweek.user.User;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("/api/auth")
@RestController
public class AuthRestController {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthServiceImpl authServiceImpl;

    @Autowired
    public AuthRestController(JwtTokenProvider inJwtTokenProvider, AuthServiceImpl inAuthServiceImpl) {
        this.jwtTokenProvider = inJwtTokenProvider;
        this.authServiceImpl = inAuthServiceImpl;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> PostMappingLogin(@RequestBody SignupData registerData) {

        Optional<User> user = authServiceImpl.signup(registerData);

        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> PostMappingLogin(@RequestBody LoginData loginData) {

        final Optional<User> userOp = authServiceImpl.login(loginData);

        if (userOp.isPresent()) {

            // User is authenticated
            final AuthenticationUser authUser = new AuthenticationUser(
                    userOp.get().getUsername(),
                    userOp.get().getPassword(),
                    true,
                    userOp.get().getRoles()
            );

            // Generate JWT token
            final String jwtToken = jwtTokenProvider.generateToken(authUser);

            // Build JWT authentication response
            JwtAuthResponse jwtAuthResponse = JwtAuthResponse.builder()
                    .accessToken(jwtToken)
                    .expiresIn(jwtTokenProvider.getJwtExpirationDate())
                    .build();

            // Return successful response with JWT token
            return ResponseEntity.ok(jwtAuthResponse);
        }

        // User not found, return UNAUTHORIZED response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
