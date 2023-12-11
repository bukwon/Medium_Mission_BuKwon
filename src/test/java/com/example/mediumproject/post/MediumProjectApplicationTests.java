package com.example.mediumproject.post;

import com.example.mediumproject.post.Post;
import com.example.mediumproject.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class MediumProjectApplicationTests {

    @Autowired
    private PostRepository postRepository;

    @Test
    void testJpa() {
        Post p1 = new Post();
        p1.setSubject("Post 어떻게 만들까?");
        p1.setContent("Post에 대해 알고 싶습니다.");
        p1.setCreateDate(LocalDateTime.now());
        this.postRepository.save(p1);   // 첫 게시글

        Post p2 = new Post();
        p2.setSubject("블로그 질문입니다.");
        p2.setContent("ID는 자동 생성되나요?.");
        p2.setCreateDate(LocalDateTime.now());
        this.postRepository.save(p2);   // 두 번째 게시글
    }

}
