package com.example.mediumproject.user;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class KakaoLoginParams implements OAuthLoginParams{ // 파라미터 전달 클래스
    private String authorizationCode;

    @Override
    public OAuthProvider oAuthProvider(){
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}