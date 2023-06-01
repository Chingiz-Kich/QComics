package kz.comics.account.repository;

import kz.comics.account.repository.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Integer> {
    Optional<LikeEntity> findByCommentEntityAndUserEntity(CommentEntity commentEntity, UserEntity userEntity);
}
