package com.isiweek;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Obtener detalles del error
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes((WebRequest) getRequestAttributes(request),
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE,
                        ErrorAttributeOptions.Include.EXCEPTION,
                        ErrorAttributeOptions.Include.STACK_TRACE)
        );

        // Agregar detalles del error al modelo
        model.addAttribute("status", errorDetails.get("status"));
        model.addAttribute("error", errorDetails.get("error"));
        model.addAttribute("message", errorDetails.get("message"));
        model.addAttribute("timestamp", errorDetails.get("timestamp"));
        model.addAttribute("path", errorDetails.get("path"));

        // Devolver la vista de error personalizada
        return "error";
    }

    private HttpServletRequest getRequestAttributes(HttpServletRequest request) {
        return (HttpServletRequest) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
    }

    public String getErrorPath() {
        return "/error";
    }
}
