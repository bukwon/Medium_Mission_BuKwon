package com.example.mediumproject.domain.user.dao;

import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.domain.user.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(String username);
    Optional<SiteUser> findById(Long id);

    Optional<SiteUser> findByLoginIdAndProvider(String email, SocialProvider provider);

    Optional<SiteUser> findByEmail(String username);
}
