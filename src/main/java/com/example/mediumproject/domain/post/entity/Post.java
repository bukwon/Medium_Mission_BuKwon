package com.example.mediumproject.domain.post.entity;

import com.example.mediumproject.domain.comment.entity.Comment;
import com.example.mediumproject.domain.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToOne
    private SiteUser author;

    private String userName;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;

    private Boolean ROLE_PAID;

    private Integer voteNum;
}