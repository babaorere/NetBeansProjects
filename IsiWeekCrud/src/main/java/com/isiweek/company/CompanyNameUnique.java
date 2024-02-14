package com.isiweek.company;

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
        validatedBy = CompanyNameUnique.CompanyNameUniqueValidator.class
)
public @interface CompanyNameUnique {

    String message() default "{Exists.company.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CompanyNameUniqueValidator implements ConstraintValidator<CompanyNameUnique, String> {

        private final CompanyService companyService;
        private final HttpServletRequest request;

        public CompanyNameUniqueValidator(final CompanyService companyService,
                final HttpServletRequest request) {
            this.companyService = companyService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // No value present, consider it valid to avoid null issues
                return true;
            }

            final Map<String, String> pathVariables = getRequiredPathVariables();
            final String currentId = pathVariables.get("id");

            if (currentId != null) {
                try {
                    final Company existingCompany = companyService.get(Long.parseLong(currentId))
                            .orElseThrow(() -> new IllegalStateException("Cannot retrieve existing company"));
                    if (value.equalsIgnoreCase(existingCompany.getName())) {
                        // Name hasn't changed, consider it valid
                        return true;
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid ID format gracefully
                    cvContext.disableDefaultConstraintViolation();
                    cvContext.buildConstraintViolationWithTemplate("Invalid company ID format")
                            .addConstraintViolation();
                    return false;
                }
            }

            try {
                return !companyService.nameExists(value);
            } catch (Exception e) {
                // Handle unexpected exceptions during the `nameExists` call
                cvContext.disableDefaultConstraintViolation();
                cvContext.buildConstraintViolationWithTemplate("Error checking name uniqueness")
                        .addConstraintViolation();
                return false;
            }
        }

        private Map<String, String> getRequiredPathVariables() {
            final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (pathVariables == null) {
                throw new IllegalStateException("Path variables not available in request");
            }
            return pathVariables;
        }

    }

}
