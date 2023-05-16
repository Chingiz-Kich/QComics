package kz.comics.account.repository;

import kz.comics.account.repository.entities.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Integer> {
}
