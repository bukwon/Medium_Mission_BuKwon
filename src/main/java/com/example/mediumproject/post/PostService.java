package com.example.mediumproject.post;

import com.example.mediumproject.comment.Comment;
import com.example.mediumproject.user.SiteUser;
import com.example.mediumproject.user.UserRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.example.mediumproject.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
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
    public List<Post> getList() {
        return this.postRepository.findAll();
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public Page<Post> getList(int page, String order, String kw, Boolean isPaid) {
        Pageable pageable = null;

        SiteUser siteUser = getCurrentUser();

        // 람다 표현식 내에서 사용할 final 변수를 생성합니다.
        final boolean isPaidValue = (isPaid == null) ? siteUser.getROLE_PAID() : isPaid;

        if (!isPaidValue) {
            if (order.equals("latest")) {
                pageable = PageRequest.of(page, 12, Sort.by("createDate").descending());
            } else if (order.equals("oldest")) {
                pageable = PageRequest.of(page, 12, Sort.by("createDate").ascending());
            } else {
                // 기본적으로 최신순으로 설정
                pageable = PageRequest.of(page, 12, Sort.by("createDate").descending());
            }
        } else {
            pageable = PageRequest.of(page, 12, Sort.by("createDate").descending());
        }

        Specification<Post> spec = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("ROLE_PAID"), !isPaidValue);
            if (kw != null && !kw.isEmpty()) {
                Predicate kwPredicate = criteriaBuilder.like(root.get("yourField"), "%" + kw + "%");
                predicate = criteriaBuilder.and(predicate, kwPredicate);
            }
            return predicate;
        };

        return this.postRepository.findAll(spec, pageable);
    }


    public void create(String subject, String content, SiteUser user) {
        Post p = new Post();
        p.setSubject(subject);
        p.setContent(content);
        p.setCreateDate(LocalDateTime.now());
        p.setAuthor(user);
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
        this.postRepository.save(post);
    }

    private SiteUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Optional<SiteUser> optionalUser = userRepository.findByUsername(authentication.getName());
        return optionalUser.orElse(null);
    }
}