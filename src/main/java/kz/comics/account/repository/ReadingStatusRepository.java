package kz.comics.account.repository;

import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ReadingStatusEntity;
import kz.comics.account.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingStatusRepository extends JpaRepository<ReadingStatusEntity, Integer> {
    void deleteByUserEntityAndChapterEntity(UserEntity userEntity, ChapterEntity chapterEntity);
    Optional<ReadingStatusEntity> findByUserEntityAndChapterEntity(UserEntity userEntity, ChapterEntity chapterEntity);
}
