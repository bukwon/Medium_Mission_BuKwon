package com.example.mediumproject.domain.comment.dao;

import com.example.mediumproject.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
// Comment의 PK를 지정하기 위한 레포지토리