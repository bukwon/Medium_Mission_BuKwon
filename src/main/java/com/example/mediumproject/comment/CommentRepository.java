package com.example.mediumproject.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
// Comment의 PK를 지정하기 위한 레포지토리