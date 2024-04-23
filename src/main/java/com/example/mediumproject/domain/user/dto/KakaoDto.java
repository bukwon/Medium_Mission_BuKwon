package com.example.mediumproject.domain.user.dto;

import com.example.mediumproject.domain.user.entity.SocialProvider;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import static jakarta.persistence.EnumType.STRING;

@Builder
@Data
public class KakaoDto {
    private long id;
    private String profileImg;
    private String email;
    private String nickname;
    @Enumerated(STRING)
    private SocialProvider provider;
}
