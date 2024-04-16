package com.example.mediumproject.domain.user.service;

import com.example.mediumproject.domain.user.dao.UserRepository;
import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.global.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public SiteUser create(String username, String email, String password, boolean ROLE_PAID)
    {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setROLE_PAID(ROLE_PAID);
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if(siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public boolean hasPaidAccess(Principal principal) {
        if (principal == null) {
            return false;
        }

        Authentication authentication = (Authentication) principal;

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("ROLE_PAID") ||
                                grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}