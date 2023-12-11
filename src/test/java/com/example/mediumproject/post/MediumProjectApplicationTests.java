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
    private PostService postService;

    @Test
    void testJpa() {
        for (int i = 1; i < 300; i++) {
            String subject = String.format("테스트 블로그입니다:[%03d]",i);
            String content = "내용 무";
            this.postService.create(subject, content);
        }
    }
}