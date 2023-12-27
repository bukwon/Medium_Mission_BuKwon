package com.example.mediumproject.user;

import com.example.mediumproject.post.Post;
import com.example.mediumproject.post.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class UserController {
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

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

        userCreateForm.setROLE_PAID(false);
        userService.create(userCreateForm.getUsername(),
                userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getROLE_PAID());

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
        model.addAttribute("user", siteUser);
        return "my_account";
    }   // 내 계정으로 파싱

    @GetMapping("/membership")  // get은 뷰하거나 리다이렉트(간혹) post는 IDU 할 때--> redirect
    public String updateMembership(Principal principal, @RequestParam boolean isPaid)
    {
        Post p = new Post();
        Optional<SiteUser> optionalUser = userRepository.findByUsername(principal.getName());
        if(optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();
            user.setROLE_PAID(isPaid);
            p.setROLE_PAID(isPaid);     // post에도 유료화인지 아닌지 저장
            userRepository.save(user);
            /*ResponseEntity.ok("Membership status updated successfully");*/
            return "redirect:/home/account";
        } else {
            return "redirect:/home/login";
                    /*ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")*/
        }
    }   // Get -> Post
}