package kz.comics.account.repository;

import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.entities.LikeEntity;
import kz.comics.account.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndComics(UserEntity userEntity, ComicsEntity comicsEntity);
}
