package kz.comics.account.repository;

import kz.comics.account.repository.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReposiroty extends JpaRepository<CommentEntity, Integer> {
}
