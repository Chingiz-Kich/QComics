package kz.comics.account.util;

import kz.comics.account.repository.entities.ComicsEntity;
import org.springframework.data.jpa.domain.Specification;

public class ComicSpecification {

    public static Specification<ComicsEntity> orderByAsc(String field) {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get(field)));
            return query.getRestriction();
        };
    }

    public static Specification<ComicsEntity> orderByDesc(String field) {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get(field)));
            return query.getRestriction();
        };
    }
}
