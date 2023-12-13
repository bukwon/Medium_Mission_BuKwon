package com.example.mediumproject.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NaverTokens {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;
}
