package com.soapapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SoapEndPoint {

    @GetMapping("/soapapp2")
    @ResponseBody
    public String SoapGetRequest(@RequestBody String soapRequest) {
        // Procesa la solicitud SOAP aquí
        // Puedes agregar la lógica necesaria para manejar la solicitud SOAP

        // Por ahora, simplemente devuelve un mensaje de éxito
        return "GET Solicitud SOAP recibida y procesada con éxito";
    }

    @PostMapping("/soapapp2")
    @ResponseBody
    public String SoapPostRequest(@RequestBody String soapRequest) {
        // Procesa la solicitud SOAP aquí
        // Puedes agregar la lógica necesaria para manejar la solicitud SOAP

        // Por ahora, simplemente devuelve un mensaje de éxito
        return "POST Solicitud SOAP recibida y procesada con éxito";
    }
}
