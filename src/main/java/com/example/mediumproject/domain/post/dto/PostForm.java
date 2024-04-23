package com.example.mediumproject.domain.post.dto;

import com.example.mediumproject.domain.post.entity.Post;
import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.domain.user.entity.UserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostForm {
    @NotEmpty(message = "제목을 입력해주세요")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    private Boolean ROLE_PAID;  // 게시물 멤버쉽 혹은 무료 선택

    public Post toEntity(SiteUser siteUser) {
        return Post.builder()
                .userName(siteUser.getUsername())
                .modifyDate(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .voteNum(0)
                .ROLE_PAID(false)
                .content(content)
                .subject(subject)
                .author(siteUser)
                .build();
    }
}
