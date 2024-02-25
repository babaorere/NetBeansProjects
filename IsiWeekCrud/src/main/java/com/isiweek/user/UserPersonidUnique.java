package com.isiweek.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Validate that the personid value isn't taken yet.
 */
@Target({FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = UserPersonidUnique.UserPersonidUniqueValidator.class
)
public @interface UserPersonidUnique {

    String message() default "{Exists.user.personid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UserPersonidUniqueValidator implements ConstraintValidator<UserPersonidUnique, Long> {

        private final UserService userService;
        private final HttpServletRequest request;

        public UserPersonidUniqueValidator(final UserService userService,
                final HttpServletRequest request) {
            this.userService = userService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }

            @SuppressWarnings("unchecked")
            final Map<String, String> pathVariables
                    = ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");

            if (currentId != null && value.equals(userService.get(Long.parseLong(currentId)).getId())) {
                // value hasn't changed
                return true;
            }

            return !userService.personidExists(value);
        }

    }

}
