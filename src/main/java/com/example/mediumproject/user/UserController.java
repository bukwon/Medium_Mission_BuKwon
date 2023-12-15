package com.example.mediumproject.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "redirect:/blog/list";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}