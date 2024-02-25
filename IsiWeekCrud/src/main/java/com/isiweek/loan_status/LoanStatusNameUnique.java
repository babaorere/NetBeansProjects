package com.isiweek.loan_status;

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
        validatedBy = LoanStatusNameUnique.LoanStatusNameUniqueValidator.class
)
public @interface LoanStatusNameUnique {

    String message() default "{Exists.loanStatus.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class LoanStatusNameUniqueValidator implements ConstraintValidator<LoanStatusNameUnique, String> {

        private final LoanStatusService loanStatusService;
        private final HttpServletRequest request;

        public LoanStatusNameUniqueValidator(final LoanStatusService loanStatusService,
                final HttpServletRequest request) {
            this.loanStatusService = loanStatusService;
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
            if (currentId != null && value.equalsIgnoreCase(loanStatusService.get(Long.parseLong(currentId)).getName())) {
                // value hasn't changed
                return true;
            }
            return !loanStatusService.nameExists(value);
        }

    }

}
