package kz.comics.account.repository;

import kz.comics.account.repository.entities.ComicEntity;
import kz.comics.account.repository.entities.LikeEntity;
import kz.comics.account.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndComics(UserEntity userEntity, ComicEntity comicEntity);
}
