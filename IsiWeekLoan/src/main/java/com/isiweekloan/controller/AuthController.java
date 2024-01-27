package com.isiweekloan.controller;

import com.isiweekloan.security.LoginForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@NoArgsConstructor
public class AuthController {

    /**
     *
     * @param loginForm
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        // Aquí debes implementar la lógica de autenticación y generar un token JWT si es exitosa
        // Puedes usar Spring Security para gestionar la autenticación

        // Ejemplo básico:
        if (authenticationSuccessful(loginForm)) {
            String token = generateJwtToken(loginForm.getUsername());
            return ResponseEntity.ok("Login successful. Token: " + token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid credentials.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationForm registrationForm) {
        // Aquí debes implementar la lógica de registro de usuarios

        // Ejemplo básico:
        if (registrationSuccessful(registrationForm)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed. Please check your data.");
        }
    }

    // Métodos de ejemplo para simular la lógica de autenticación y registro
    private boolean authenticationSuccessful(LoginForm loginForm) {
        // Implementa la lógica de autenticación real aquí
        // Puedes usar Spring Security o tu propia lógica de autenticación
        return "username".equals(loginForm.getUsername()) && "password".equals(loginForm.getPassword());
    }

    private boolean registrationSuccessful(RegistrationForm registrationForm) {
        // Implementa la lógica de registro real aquí
        // Puedes usar un servicio de usuario para gestionar el registro
        // y almacenar la información del usuario en tu base de datos
        return registrationForm != null && !registrationForm.getUsername().isEmpty() && !registrationForm.getPassword().isEmpty();
    }

    // Método para generar un token JWT (Debes implementar tu lógica real aquí)
    private String generateJwtToken(String username) {
        // Lógica para generar el token JWT
        // (Este es solo un ejemplo, debes implementar tu propia lógica)
        return "example-jwt-token";
    }

    // Clase de formulario para la solicitud de registro
    @Getter
    @Setter
    private static class RegistrationForm {

        private String username;
        private String password;

        // Getters y setters
    }
}
