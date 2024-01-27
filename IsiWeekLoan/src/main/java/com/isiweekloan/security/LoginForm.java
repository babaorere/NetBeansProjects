package com.isiweekloan.security;

// Clase de formulario para la solicitud de inicio de sesió
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {

    private String username;
    private String password;

}
