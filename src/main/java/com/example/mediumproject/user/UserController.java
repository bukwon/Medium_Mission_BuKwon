package com.example.mediumproject.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Set;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult
                         bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }

        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        /*if(this.userService.findByUserName(userCreateForm.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "usernameIncorrect", "사용자가 일치하지 않습니다.");
            return "signup_form";
        }
        if(!userCreateForm.getEmail().equals(userCreateForm.getEmail())) {
            bindingResult.rejectValue("id", "idInCorrect", "id가 일치하지 않습니다.");
            return "signup_form";
        }*/
        userService.create(userCreateForm.getUsername(),
                userCreateForm.getEmail(), userCreateForm.getPassword1());

        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/account")
    public String myAccount(Principal principal, Model model) {
        String username = principal.getName();
        SiteUser siteUser = this.userService.getUser(username);
        model.addAttribute("username", siteUser.getUsername());
        model.addAttribute("email", siteUser.getEmail());
        return "my_account";
    }   // 내 계정으로 파싱
}