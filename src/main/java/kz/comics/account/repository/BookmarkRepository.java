package kz.comics.account.repository;

import kz.comics.account.repository.entities.BookmarkEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Integer> {

    Optional<BookmarkEntity> findByUserAndComic(UserEntity userEntity, ComicsEntity comicsEntity);
}
