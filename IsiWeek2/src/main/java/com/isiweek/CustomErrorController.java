package com.isiweek;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/public/isiweek-error");
        modelAndView.addObject("title", "An error occurred on the page.");

        // Obtener el código de estado del error
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        // Obtener el mensaje de error
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");

        // Obtener la excepción que causó el error
        Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");

        modelAndView.addObject("statusCode", "Status: " + String.valueOf(statusCode));
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.addObject("exception", exception != null ? exception.getMessage() : "No exception message");

        return modelAndView;
    }

    public String getErrorPath() {
        return "/error";
    }
}
