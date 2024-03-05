package com.isiweek.status;

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
 * Validate that the name value isn't taken yet.
 */
@Target({FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = StatusNameUnique.StatusNameUniqueValidator.class
)
public @interface StatusNameUnique {

    String message() default "{Exists.status.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class StatusNameUniqueValidator implements ConstraintValidator<StatusNameUnique, StatusEnum> {

        private final StatusService statusService;
        private final HttpServletRequest request;

        public StatusNameUniqueValidator(final StatusService statusService,
                final HttpServletRequest request) {
            this.statusService = statusService;
            this.request = request;
        }

        @Override
        public boolean isValid(final StatusEnum value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked")
            final Map<String, String> pathVariables
                    = ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");

            if (currentId != null && value.equals(statusService.get(Long.valueOf(currentId)).getName())) {
                // value hasn't changed
                return true;
            }
            return !statusService.nameExists(value);
        }

    }

}
