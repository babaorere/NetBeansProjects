package com.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String handleSubdomain(Model model) {
        model.addAttribute("subdomain", "0");
        return "subdomainPage";
    }

    @GetMapping("/main/{subdomain}")
    public String handleMainSubdomain(@PathVariable String subdomain, Model model) {
        model.addAttribute("subdomain", subdomain);
        String page = "subdomain" + subdomain;
        return "main/subdomain";
    }
}
