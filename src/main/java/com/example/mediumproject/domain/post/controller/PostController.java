package com.example.mediumproject.domain.post.controller;


import com.example.mediumproject.domain.comment.dto.CommentForm;
import com.example.mediumproject.domain.post.dto.PostForm;
import com.example.mediumproject.domain.post.service.PostService;
import com.example.mediumproject.domain.post.entity.Post;
import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.domain.user.dao.UserRepository;
import com.example.mediumproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/blog")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "order", defaultValue = "latest") String order,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {

        // 추가: order 값이 null이면 빈 문자열로 처리
        if (order == null || order.equals("null")) {
            order = "";
        }

        Page<Post> paging = this.postService.getList(page, order, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("order", order);  // 추가: order 값을 모델에 추가
        return "post_list";
    }


    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, Principal principal, CommentForm commentForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);

        boolean hasAccess = principal != null &&
                (post.getAuthor().getUsername().equals(principal.getName()) ||
                        userService.hasPaidAccess(principal));

        // 유료 콘텐츠이면서 접근 권한이 없는 경우
        if (post.getROLE_PAID() && !hasAccess) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유료회원 전용 콘텐츠입니다.");
        }   // 54 ~ 59는 포스트 권한 처리이므로 서비스 이동 권고
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm, Model model) {
        model.addAttribute("post", postForm);
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult, Principal principal, Model model) {
        System.out.println("create생성 거쳤습니다.");
        model.addAttribute("post", postForm);
        if(bindingResult.hasErrors()) {
            System.out.println("bindingResult = " + bindingResult);
            System.out.println("binding 통과");
            return "post_form";
        }
        System.out.println("로그인 유효성 검사 통과");
        System.out.println("principal = " + principal.getName());
        SiteUser siteUser = this.userService.getUser(principal.getName());
        System.out.println("블로그 생성할 때 login 상태 유효성은 검사합니다" + siteUser);
        this.postService.create(siteUser, postForm);
        return "redirect:/blog/list";   // 게시글 적은 후 돌아갈 url
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(PostForm postForm, @PathVariable("id") Integer id, Principal principal) {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        postForm.setSubject(post.getSubject());
        postForm.setContent(post.getContent());
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/storage")
    public String storageList() {
        return "storage";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid PostForm postForm, BindingResult bindingResult,
                             Principal principal, @PathVariable("id") Integer id) {
        if(bindingResult.hasErrors()) {
            return "post_form";
        }
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.modify(post, postForm.getSubject(), postForm.getContent());
        return String.format("redirect:/blog/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }
        this.postService.delete(post);
        return "redirect:/blog/list";
    }   // 수정 쪽은 post


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String postVote(Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.postService.vote(post, siteUser);
        return String.format("redirect:/blog/detail/%s", id);}
}