package com.isiweek.status_entity;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the name value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = StatusEntityNameUnique.StatusEntityNameUniqueValidator.class
)
public @interface StatusEntityNameUnique {

    String message() default "{Exists.statusEntity.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class StatusEntityNameUniqueValidator implements ConstraintValidator<StatusEntityNameUnique, StatusEnum> {

        private final StatusEntityService statusEntityService;
        private final HttpServletRequest request;

        public StatusEntityNameUniqueValidator(final StatusEntityService statusEntityService,
                final HttpServletRequest request) {
            this.statusEntityService = statusEntityService;
            this.request = request;
        }

        @Override
        public boolean isValid(final StatusEnum value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables = 
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(statusEntityService.get(Long.parseLong(currentId)).getName())) {
                // value hasn't changed
                return true;
            }
            return !statusEntityService.nameExists(value);
        }

    }

}
