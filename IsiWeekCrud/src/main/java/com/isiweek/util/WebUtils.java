package com.isiweek.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Field;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

@Component
public class WebUtils {

    public static final String MSG_SUCCESS = "MSG_SUCCESS";
    public static final String MSG_INFO = "MSG_INFO";
    public static final String MSG_ERROR = "MSG_ERROR";
    private static MessageSource messageSource;
    private static LocaleResolver localeResolver;

    public WebUtils(final MessageSource messageSource, final LocaleResolver localeResolver) {
        WebUtils.messageSource = messageSource;
        WebUtils.localeResolver = localeResolver;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        } else {
            // Handle the case where RequestContextHolder.getRequestAttributes() is null
            throw new IllegalStateException("Unable to obtain HttpServletRequest, RequestAttributes is null.");
        }
    }

    public static String getMessage(final String code, final Object... args) {
        return messageSource.getMessage(code, args, code, localeResolver.resolveLocale(getRequest()));
    }

    public static boolean isRequiredField(final Object dto, final String fieldName) {
        if (dto == null || fieldName == null) {
            throw new IllegalArgumentException("DTO and fieldName must not be null");
        }

        try {
            Field field = dto.getClass().getDeclaredField(fieldName);
            NotNull annotation = field.getAnnotation(NotNull.class);
            return annotation != null;
        } catch (NoSuchFieldException | SecurityException e) {
            // Handle the case where the field does not exist or there is a security exception
            return false;
        }
    }
}
