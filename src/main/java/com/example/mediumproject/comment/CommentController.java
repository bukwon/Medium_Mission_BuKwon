package com.example.mediumproject.comment;

import com.example.mediumproject.post.Post;
import com.example.mediumproject.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id,
                                @Valid CommentForm commentForm, BindingResult bindingResult) {
        Post post = this.postService.getPost(id);   // 답변저장 구간
        if(bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "post_detail";
        }

        this.commentService.create(post, commentForm.getContent());
        return String.format("redirect:/blog/detail/%s", id);
    }
}
