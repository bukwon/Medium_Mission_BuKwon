package com.example.mediumproject.post;

import lombok.RequiredArgsConstructor;
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

    public void create(String subject, String content) {
        Post p = new Post();
        p.setSubject(subject);
        p.setContent(content);
        p.setCreateDate(LocalDateTime.now());
        this.postRepository.save(p);
    }
}
