package com.example.mediumproject.post;

import com.example.mediumproject.post.Post;
import com.example.mediumproject.post.PostRepository;
import com.example.mediumproject.user.SiteUser;
import com.example.mediumproject.user.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MediumProjectApplicationTests {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Test
    void testJpa() {
        for (int i = 1; i < 50; i++) {
            String subject = String.format("테스트 블로그입니다:[%03d]",i);
            String content = "내용 무";
            Boolean ROLE_PAID = false;
            this.postService.create(subject, content, userService.create(String.valueOf(i),String.valueOf(i)+"@naver.com", String.valueOf(i),ROLE_PAID));
        }
    }


}

@SpringBootTest
class CreateJwtTest {

    @Value("${jwt.secret-key}")
    private String secretKeyPlain;

    @Test
    void 시크릿키_존재_확인() {
        assertThat(secretKeyPlain).isNotNull();
    }
}