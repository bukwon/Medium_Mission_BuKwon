package com.example.mediumproject.comment;

import com.example.mediumproject.post.Post;
import com.example.mediumproject.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne  // 하나의 게시글에 여러개 댓글 달기 가능
    private Post post;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;
}