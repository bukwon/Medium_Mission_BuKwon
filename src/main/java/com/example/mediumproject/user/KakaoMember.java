package com.example.mediumproject.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

public class KakaoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private OAuthProvider oAuthProvider;

    @Builder
    public KakaoMember(String email, String nickname, OAuthProvider oAuthProvider) {
        this.email=email;
        this.nickname=nickname;
        this.oAuthProvider=oAuthProvider;
    }
}
