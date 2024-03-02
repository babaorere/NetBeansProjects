package com.isiweek.auth;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequestMapping("/auth")
@Controller
public class AuthController {

    @Autowired
    public AuthController() {
    }

    @GetMapping("/signup")
    public String GetMappingSignup(@ModelAttribute("data") final SignupData signupData) {

        return "auth/signup";
    }

    @GetMapping("/login")
    public String GetMappingLogin(@ModelAttribute("data") final LoginData loginData) {

        return "auth/login";
    }

}
