package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ejemplo")
public class HomeController {

    @GetMapping("/saludo")
    public String obtenerSaludo(Model model) {
        model.addAttribute("mensaje", "¡Hola desde el controlador!");
        return "saludo";
    }
}
