package joo.example.springboot2security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SignInController {

    @GetMapping("/signin")
    public String signIn() {
        return "login";
    }
}
