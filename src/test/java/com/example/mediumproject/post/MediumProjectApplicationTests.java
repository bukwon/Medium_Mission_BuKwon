package com.example.mediumproject.post;

import com.example.mediumproject.domain.post.dto.PostForm;
import com.example.mediumproject.domain.post.service.PostService;
import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.domain.user.entity.UserRole;
import com.example.mediumproject.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        for (int i = 1; i <= 100; i++) {
            String subject = String.format("무료글:[%03d]",i);
            String content = "내용 무";
            boolean ROLE_PAID = false;
            this.postService.create(userService.create("user" + i, "email" + i + "@naver.com", "", false)
                    , null);
        }
        for (int i = 101; i < 201; i++) {
            String subject = String.format("유료글:[%03d]",i);
            String content = "내용 무";
            boolean ROLE_PAID = true;
            this.postService.create(userService.create("user" + i, "email" + i + "@naver.com", "", false)
                    , null);
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