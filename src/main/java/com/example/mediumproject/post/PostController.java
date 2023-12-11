package com.example.mediumproject.post;

import com.example.mediumproject.comment.Comment;
import com.example.mediumproject.comment.CommentForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RequestMapping("/blog")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> postList = this.postService.getList();
        model.addAttribute("postList", postList);
        return "post_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/create")
    public String postCreate(PostForm postForm) {
        return "post_form";
    }

    @PostMapping("/create")
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "post_form";
        }
        this.postService.create(postForm.getSubject(), postForm.getContent());
        return "redirect:/blog/list";   // 게시글 적은 후 돌아갈 url
    }
}
