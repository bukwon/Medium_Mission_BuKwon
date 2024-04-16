package com.example.mediumproject.domain.post.service;

import com.example.mediumproject.domain.comment.entity.Comment;
import com.example.mediumproject.domain.post.dao.PostRepository;
import com.example.mediumproject.domain.post.entity.Post;
import com.example.mediumproject.domain.user.entity.SiteUser;
import com.example.mediumproject.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.example.mediumproject.global.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private Specification<Post> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Post> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Post, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Post, Comment> a = q.join("commentList", JoinType.LEFT);
                Join<Comment, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }
    // 세션을 통한 로그인 지속 구현
    public Page<Post> getList(int page, String order, String kw) {
        Pageable pageable;

        if (order.equals("latest")) {
            pageable = PageRequest.of(page, 12, Sort.by("createDate").descending());
        } else if (order.equals("oldest")) {
            pageable = PageRequest.of(page, 12, Sort.by("createDate").ascending());
        } else if (order.equals("recommend")) {
            pageable = PageRequest.of(page, 12, Sort.by("voteNum").descending());
        }else if (order.equals("unrecommend")) {
            pageable = PageRequest.of(page, 12, Sort.by("voteNum").ascending());
        } else {
            // 기본적으로 최신순으로 설정
            pageable = PageRequest.of(page, 12, Sort.by("createDate").descending());
        }

        Specification<Post> spec = search(kw);
        return this.postRepository.findAll(spec, pageable);
    }

    public void create(String subject, String content, SiteUser user) {
        Post p = new Post();
        p.setSubject(subject);
        p.setContent(content);
        p.setCreateDate(LocalDateTime.now());
        p.setAuthor(user);
        p.setUserName(user.getUsername());
        p.setROLE_PAID(user.getROLE_PAID());
        this.postRepository.save(p);
    }

    public void modify(Post post, String subject, String content) {
        post.setSubject(subject);
        post.setContent(content);
        post.setModifyDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    public void vote(Post post, SiteUser siteUser) {
        post.getVoter().add(siteUser);
        post.setVoteNum(+1);
        this.postRepository.save(post);
    }
}