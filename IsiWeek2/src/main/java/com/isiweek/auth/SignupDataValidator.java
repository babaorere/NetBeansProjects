package com.isiweek.auth;

import com.isiweek.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SignupDataValidator implements ConstraintValidator<ValidSignupData, SignupData> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(SignupData signupData, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (signupData.getEmail() == null || signupData.getEmail().isBlank()) {
            context.buildConstraintViolationWithTemplate("The email field cannot be null or empty.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        if (signupData.getIdRole() == null || signupData.getIdRole() == 0) {
            context.buildConstraintViolationWithTemplate("The role field cannot be null.")
                    .addPropertyNode("idRole")
                    .addConstraintViolation();
            return false;
        }

        // Verifica que la contraseña y la confirmación de contraseña coincidan
        if (!signupData.getPassword().equals(signupData.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Passwords do not match.")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }

        // Verificar si el usuario ya existe (solo si el email es válido)
        if (userRepository.existsByEmailIgnoreCase(signupData.getEmail())) {
            context.buildConstraintViolationWithTemplate("User with email: " + signupData.getEmail() + " already exists.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
