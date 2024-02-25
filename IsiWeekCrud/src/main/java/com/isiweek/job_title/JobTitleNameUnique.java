package com.isiweek.job_title;

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
        validatedBy = JobTitleNameUnique.JobTitleNameUniqueValidator.class
)
public @interface JobTitleNameUnique {

    String message() default "{Exists.jobTitle.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class JobTitleNameUniqueValidator implements ConstraintValidator<JobTitleNameUnique, String> {

        private final JobTitleService jobTitleService;
        private final HttpServletRequest request;

        public JobTitleNameUniqueValidator(final JobTitleService jobTitleService,
                final HttpServletRequest request) {
            this.jobTitleService = jobTitleService;
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
            if (currentId != null && value.equalsIgnoreCase(jobTitleService.get(Long.parseLong(currentId)).getName())) {
                // value hasn't changed
                return true;
            }
            return !jobTitleService.nameExists(value);
        }

    }

}
