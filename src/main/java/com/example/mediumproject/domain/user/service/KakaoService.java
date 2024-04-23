package com.example.mediumproject.domain.user.service;

import com.example.mediumproject.domain.user.dao.UserRepository;
import com.example.mediumproject.domain.user.dto.KakaoDto;
import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.domain.user.entity.SocialProvider;
import com.example.mediumproject.domain.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URL;

    @Value("${kakao.auth.uri}")
    private String KAKAO_AUTH_URI;

    @Value("${kakao.api.uri}")
    private String KAKAO_API_URI;

    private final UserRepository userRepository;

    public String getKakaoUrl() {
        return KAKAO_AUTH_URI + "/oauth/authorize" +
                "?client_id=" + KAKAO_CLIENT_ID +
                "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    }

    public KakaoDto getUserInfo(String access_token) {
        String host = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while ((line = br.readLine()) != null) {
                res += line;
            }

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(res);
            JSONObject properties = (JSONObject)obj.get("properties");
            JSONObject kakaoAccount = (JSONObject)obj.get("kakao_account");
            String id = obj.get("id").toString();
            String nickname = properties.get("nickname").toString();
            Object profileImgObject = properties.get("profile_image");
            String profileImg = (profileImgObject != null) ? profileImgObject.toString() :
                    null;  // 프로필 이미지 동의 안할 시 null 값 대신 기본 logo.png로 대체

            String email = kakaoAccount.get("email").toString();

            System.out.println("카카오 로그인 확인" + parser);

            return KakaoDto.builder()
                    .id(Long.valueOf(id))
                    .email(email)
                    .nickname(nickname)
                    .profileImg(profileImg)
                    .provider(SocialProvider.KAKAO)
                    .build();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("카카오 정보를 가져오는데 실패했습니다!");
        }
    }

    public String getToken(String code) throws IOException {
        // 인가코드로 토큰받기
        String host = KAKAO_AUTH_URI + "/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        String token = "";
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // 데이터 기록 알려주기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + KAKAO_CLIENT_ID);
            sb.append("&redirect_uri=" + KAKAO_REDIRECT_URL);
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = urlConnection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject)parser.parse(result);

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return token;
    }

    public SiteUser login(KakaoDto userInfo) {
        Optional<SiteUser> opUser = userRepository.findByLoginIdAndProvider(userInfo.getEmail(),
                userInfo.getProvider());

        if (opUser.isPresent()) {
            return opUser.get();
        }

        // 강제 회원가입
        SiteUser member = SiteUser.builder()
                .loginId(userInfo.getEmail())
                .profileImg(userInfo.getProfileImg())
                .username(userInfo.getNickname())
                .provider(SocialProvider.KAKAO)
                .userRole(UserRole.USER)
                .email(userInfo.getEmail())
                .build();

        return userRepository.save(member);
    }
}
