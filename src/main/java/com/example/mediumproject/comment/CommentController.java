package com.example.mediumproject.comment;

import com.example.mediumproject.post.Post;
import com.example.mediumproject.post.PostService;
import com.example.mediumproject.user.SiteUser;
import com.example.mediumproject.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id,
                                @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Post post = this.postService.getPost(id);   // 답변저장 구간
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "post_detail";
        }

        this.commentService.create(post, commentForm.getContent(), siteUser);
        return String.format("redirect:/blog/detail/%s", id);
    }
}
