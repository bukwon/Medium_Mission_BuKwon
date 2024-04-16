package com.example.mediumproject.domain.post.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "제목을 입력해주세요")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    private Boolean ROLE_PAID;  // 게시물 멤버쉽 혹은 무료 선택
}
