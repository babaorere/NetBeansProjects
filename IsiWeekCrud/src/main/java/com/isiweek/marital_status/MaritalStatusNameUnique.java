package com.isiweek.marital_status;

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
        validatedBy = MaritalStatusNameUnique.MaritalStatusNameUniqueValidator.class
)
public @interface MaritalStatusNameUnique {

    String message() default "{Exists.maritalStatus.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MaritalStatusNameUniqueValidator implements ConstraintValidator<MaritalStatusNameUnique, String> {

        private final MaritalStatusService maritalStatusService;
        private final HttpServletRequest request;

        public MaritalStatusNameUniqueValidator(final MaritalStatusService maritalStatusService,
                final HttpServletRequest request) {
            this.maritalStatusService = maritalStatusService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables = 
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(maritalStatusService.get(Long.parseLong(currentId)).getName())) {
                // value hasn't changed
                return true;
            }
            return !maritalStatusService.nameExists(value);
        }

    }

}
