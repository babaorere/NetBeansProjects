package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soapapp.DataService;
import soapapp.RequestMessage;

@RestController
@RequestMapping("/soap-endpoint")
public class SoapEndpointController {

    @Autowired
    private DataService service;

    @GetMapping
    public String getData(RequestMessage request) {
        String text = request.getText();
        byte[] file = request.getFile();
        return service.service(text, file);
    }

    @PostMapping
    @ResponsePayload
    public String postData(@RequestPayload RequestMessage request) {
        String text = request.getText();
        byte[] file = request.getFile();
        return service.service(text, file);
    }
}
