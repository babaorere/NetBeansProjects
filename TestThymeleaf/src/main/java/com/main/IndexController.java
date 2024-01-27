package com.main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/rest")
    public String restPage() {
        return "rest";
    }

    @GetMapping("/example")
    public ResponseEntity<String> exampleEndpoint() {
        // Lógica para obtener el contenido que deseas devolver
        String content = "¡Hola, mundo!";

        // Crear un ResponseEntity personalizado con el contenido y el código de estado
        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}
