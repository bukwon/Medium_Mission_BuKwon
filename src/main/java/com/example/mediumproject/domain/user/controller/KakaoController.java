package com.example.mediumproject.domain.user.controller;

import com.example.mediumproject.domain.user.dto.KakaoDto;
import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.domain.user.service.KakaoService;
import com.example.mediumproject.global.common.MsgEntity;
import com.example.mediumproject.global.security.SecurityUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Member;

import static com.example.mediumproject.domain.user.entity.SocialProvider.KAKAO;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {
    private final KakaoService ks;
    @GetMapping("/callback")
    public String getCI(@RequestParam String code, Model model, HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        String access_token = ks.getToken(code);
        System.out.println("accessToken = " + access_token);
        KakaoDto userInfo = ks.getUserInfo(access_token);

        SiteUser siteUser = ks.login(userInfo);

        SecurityUser securityUser = new SecurityUser(siteUser.getId(), siteUser.getLoginId(),
                "", "", siteUser.getEmail(),
                siteUser.getAuthorities());

        Authentication authentication =
                new OAuth2AuthenticationToken(securityUser, securityUser.getAuthorities(), KAKAO.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 새로운 세션 생성
        session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        // 세션 ID를 쿠키에 설정
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        //ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
        return "redirect:/";
    }
}
