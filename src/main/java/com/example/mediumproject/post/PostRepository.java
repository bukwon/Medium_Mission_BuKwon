package com.example.mediumproject.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
// PK를 지정하기 위한 공간 (메인 키 값은 Integer)