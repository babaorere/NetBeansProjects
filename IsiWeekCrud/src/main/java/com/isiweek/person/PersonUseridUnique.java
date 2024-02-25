package com.isiweek.person;

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
 * Validate that the userid value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = PersonUseridUnique.PersonUseridUniqueValidator.class
)
public @interface PersonUseridUnique {

    String message() default "{Exists.person.userid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PersonUseridUniqueValidator implements ConstraintValidator<PersonUseridUnique, Long> {

        private final PersonService personService;
        private final HttpServletRequest request;

        public PersonUseridUniqueValidator(final PersonService personService,
                final HttpServletRequest request) {
            this.personService = personService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables = 
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(personService.get(Long.parseLong(currentId)).getUserid())) {
                // value hasn't changed
                return true;
            }
            return !personService.useridExists(value);
        }

    }

}
