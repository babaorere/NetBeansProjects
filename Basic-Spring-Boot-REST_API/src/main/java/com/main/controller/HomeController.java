package com.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        System.out.println("public String index()");
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        System.out.println("public String about()");
        return "about";
    }

    @GetMapping("/menu")
    public String menu() {
        System.out.println("public String menu()");
        return "menu";
    }
}
