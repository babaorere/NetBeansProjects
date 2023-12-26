package com.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index() {
        return "index.html";
    }

    @GetMapping("/rest")
    public String restPage() {
        return "rest.html";
    }
}
