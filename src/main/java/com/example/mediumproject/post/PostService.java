package com.example.mediumproject.post;

import com.example.mediumproject.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.mediumproject.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    public List<Post> getList() {
        return this.postRepository.findAll();
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public Page<Post> getList(int page ,String order) {
        Pageable pageable;
        if (order.equals("latest")) {
            pageable = PageRequest.of(page, 12, Sort.by("createDate").descending());
        } else if (order.equals("oldest")) {
            pageable = PageRequest.of(page, 12, Sort.by("createDate").ascending());
        } else {
            // 기본적으로 최신순으로 설정
            pageable = PageRequest.of(page, 12, Sort.by("createDate").descending());
        }
        return this.postRepository.findAll(pageable);
    }

    public void create(String subject, String content, SiteUser user) {
        Post p = new Post();
        p.setSubject(subject);
        p.setContent(content);
        p.setCreateDate(LocalDateTime.now());
        p.setAuthor(user);
        this.postRepository.save(p);
    }
}
