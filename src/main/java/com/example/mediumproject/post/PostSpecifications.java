package com.example.mediumproject.post;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class PostSpecifications {

    public static Specification<Post> isPaid(boolean isPaid) {
        return (root, query, criteriaBuilder) -> {
            if(isPaid) {
                return criteriaBuilder.isTrue(root.get("ROLE_PAID"));
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Post> containsKeyword(String kw) {
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.hasText(kw)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + kw.toLowerCase() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }
}
