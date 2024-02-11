package com.isiweek;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/home/maint")
    public String maintPage() {
        return "home/maint";
    }
}
