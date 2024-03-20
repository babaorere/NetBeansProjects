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
        // Verifica que el rol, el prestamista, el deudor y el estado no sean nulos
        if ((signupData.getEmail() == null)
                || signupData.getEmail().isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The email field cannot be null.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        if (signupData.getRole() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The role field cannot be null.")
                    .addPropertyNode("role")
                    .addConstraintViolation();
            return false;
        }

        // Verifica que la contraseña y la confirmación de contraseña coincidan
        if (!signupData.getPassword().equals(signupData.getConfirmPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match.")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }

        // Verificar si el usuario ya existe
        if (userRepository.existsByEmailIgnoreCase(signupData.getEmail())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("User with email: " + signupData.getEmail() + " already exists.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
        }

        return true;
    }
}
