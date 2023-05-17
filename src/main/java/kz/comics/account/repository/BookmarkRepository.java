package kz.comics.account.repository;

import kz.comics.account.repository.entities.BookmarkEntity;
import kz.comics.account.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Integer> {
    void deleteAllByUser(UserEntity userEntity);
}
