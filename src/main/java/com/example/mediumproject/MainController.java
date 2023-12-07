package com.example.mediumproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/signup_form")
    public String signup(){
        return "signup_form";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }   // 로그인 성공 시 떠야 할 창 공간
}
